package com.example.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reminder.data.repository.AndroidAlarmScheduler
import com.example.reminder.domain.repository.AlarmScheduler
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.reminder.presentation.ReminderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

val notificationModule= module {
    single<AlarmScheduler>{
        AndroidAlarmScheduler(androidContext())
    }
    viewModel {
        ReminderViewModel(
            alarmScheduler = get()
        )
    }
}