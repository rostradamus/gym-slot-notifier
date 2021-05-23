package com.rostradamus.gymslotnotifier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class GymSlotNotifierApplication

fun main(args: Array<String>) {
	runApplication<GymSlotNotifierApplication>(*args)
}
