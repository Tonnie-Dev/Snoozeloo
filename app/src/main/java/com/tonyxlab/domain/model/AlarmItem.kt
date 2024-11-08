package com.tonyxlab.domain.model

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val name: String,
    val message: String
){
}