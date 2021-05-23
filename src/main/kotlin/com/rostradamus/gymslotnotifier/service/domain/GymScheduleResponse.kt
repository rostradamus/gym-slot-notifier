package com.rostradamus.gymslotnotifier.service.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author rostradamus
 * created on 2021-05-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class GymScheduleResponse(
    @param:JsonProperty("afrom") @get:JsonProperty("afrom") val aFrom: String,
    @param:JsonProperty("ato") @get:JsonProperty("ato") val aTo: String,
    @param:JsonProperty("app") @get:JsonProperty("app") val slots: Set<GymSlot>,
)