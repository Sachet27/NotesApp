package com.example.core.mappers

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.example.notesapp.domain.Note
import com.example.reminder.domain.models.AlarmItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
fun Note.toAlarmItem(time: TimePickerState): AlarmItem{
    Log.d("Yeet", "${time.hour}: ${time.minute}")
    val calendar= Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, time.hour)
        set(Calendar.MINUTE, time.minute)
        set(Calendar.SECOND, 0)
    }
    val actualTime= Instant
        .ofEpochMilli(calendar.timeInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    Log.d("Yeet", actualTime.format(DateTimeFormatter.ofPattern("hh:mm a")))

    val alarmItem= AlarmItem(
        time = actualTime,
        message = this.title
    )
    return alarmItem
}

fun LocalDateTime.toLocalReadableTime(): String{
    return this.format(DateTimeFormatter.ofPattern("hh:mm a"))
}