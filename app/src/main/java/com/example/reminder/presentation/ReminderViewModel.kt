package com.example.reminder.presentation

import android.app.NotificationManager
import android.content.Context
import android.os.Message
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import com.example.notesapp.R
import com.example.reminder.data.repository.AndroidAlarmScheduler
import com.example.reminder.domain.models.AlarmItem
import com.example.reminder.domain.repository.AlarmScheduler
import java.time.LocalDateTime

class ReminderViewModel(
    private val alarmScheduler: AlarmScheduler
):ViewModel() {
    fun onAction(actions: ReminderActions){
        when(actions){
            is ReminderActions.onPushReminder-> {
                val alarm= actions.alarm
               scheduleReminder(alarm.time, alarm.message)
            }
            is ReminderActions.onCancelReminder->{ cancelReminder(actions.alarm)}
        }
    }

    private fun scheduleReminder(time: LocalDateTime, message: String){
        val alarmItem= AlarmItem(time=time, message=message)
        alarmScheduler.schedule(alarmItem)
    }
    private fun cancelReminder(alarmItem: AlarmItem){
        alarmScheduler.cancel(alarmItem)
    }
}