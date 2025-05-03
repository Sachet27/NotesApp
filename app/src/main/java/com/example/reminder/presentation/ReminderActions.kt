package com.example.reminder.presentation

import com.example.notesapp.domain.Note
import com.example.reminder.domain.models.AlarmItem

interface ReminderActions {
    data class onPushReminder(val alarm: AlarmItem): ReminderActions
    data class onCancelReminder(val alarm: AlarmItem): ReminderActions
}