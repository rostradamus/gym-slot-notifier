package com.rostradamus.gymslotnotifier.client

import com.rostradamus.gymslotnotifier.service.domain.GymScheduleResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

/**
 * @author rostradamus
 * created on 2021-05-23
 */
@Component
class ScheduleClient(
    @Value("\${gymslotnotifier.gymschedule.client.baseUrl}") private val baseUrl: String,
    @Value("\${gymslotnotifier.gymschedule.client.token}") private val token: String,
) {
    private val webClient = WebClient.create(baseUrl)

    fun fetchSchedule(from: LocalDateTime, to: LocalDateTime): Mono<GymScheduleResponse> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/ajax/resource/542327")
                    .queryParam("token", token)
                    .queryParam("afrom", from)
                    .queryParam("ato", to)
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::isError) { Mono.error(RuntimeException()) }
            .bodyToMono(GymScheduleResponse::class.java)
    }
}