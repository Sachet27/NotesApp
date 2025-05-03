package com.example.reminder.domain.models

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String,
    val id:Int= time.hashCode()
)
