package com.rostradamus.gymslotnotifier.service.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.time.LocalDateTime

/**
 * @author rostradamus
 * created on 2021-05-23
 */
@JsonDeserialize(using = GymSlotDeserializer::class)
data class GymSlot(
    var from: LocalDateTime,
    var to: LocalDateTime,
    var user: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GymSlot

        if (from != other.from) return false
        if (to != other.to) return false

        return true
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }
}