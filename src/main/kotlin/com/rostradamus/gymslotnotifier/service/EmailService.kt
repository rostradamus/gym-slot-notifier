package com.rostradamus.gymslotnotifier.service

import com.rostradamus.gymslotnotifier.service.domain.GymSlot
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author rostradamus
 * created on 2021-05-23
 */
@Service
class EmailService(
    val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}") val sender: String,
) {
    fun sendCancelledGymSlotNotification(gymSlots: List<GymSlot>): Mono<Boolean> {
        if (gymSlots.isEmpty()) return Mono.just(false)
        return Mono.fromCallable {
            val message = SimpleMailMessage()
            message.setFrom(sender)
            message.setTo("rolee0429@gmail.com")
            message.setSubject("Cancellation Notification")
            message.setText("Hello\n We have detected recently cancelled Gym Slot!\n $gymSlots\nBest,\nGym Slot Notifier")
            mailSender.send(message)
            true
        }
    }
}