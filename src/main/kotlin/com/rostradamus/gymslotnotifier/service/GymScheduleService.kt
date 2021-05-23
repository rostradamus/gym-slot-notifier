package com.rostradamus.gymslotnotifier.service

import com.rostradamus.gymslotnotifier.client.ScheduleClient
import com.rostradamus.gymslotnotifier.service.domain.GymScheduleResponse
import com.rostradamus.gymslotnotifier.service.domain.GymSlot
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

/**
 * @author rostradamus
 * created on 2021-05-23
 */
@Slf4j
@Service
class GymScheduleService(
    val emailService: EmailService,
    val scheduleClient: ScheduleClient,
) {
    private val logger = LoggerFactory.getLogger(GymScheduleService::class.java)
    val tracker: MutableSet<GymSlot> = ConcurrentHashMap.newKeySet()

    @Scheduled(fixedRate = 30000)
    fun fetchSchedule() {
        val now = LocalDateTime.now()
        val gymSlots: Mono<Set<GymSlot>> = scheduleClient
            .fetchSchedule(now, now.plusWeeks(1))
            .map(GymScheduleResponse::slots)
            .cache()
        gymSlots
            .flatMap(this::compareResultWithPrevious)
            .then(gymSlots)
            .doOnNext { tracker.clear() }
            .doOnNext(tracker::addAll)
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe()
    }

    fun compareResultWithPrevious(currentSlots: Set<GymSlot>): Mono<Boolean> {
        logger.info("Comparing... Tracker Size: ${tracker.size}")
        return Flux.fromIterable(tracker)
            .filter { slot -> !currentSlots.contains(slot) }
            .doOnNext { gymSlot -> println("Detected cancelled slot: ${gymSlot.from} - ${gymSlot.to}")}
            .collectList()
            .flatMap(emailService::sendCancelledGymSlotNotification)
    }
}