package com.example.reminder.domain.repository

import com.example.reminder.domain.models.AlarmItem

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}