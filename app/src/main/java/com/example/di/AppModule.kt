package com.example.di

import android.util.Log
import androidx.room.Room
import com.example.notesapp.data.datastore.createDataStore
import com.example.notesapp.domain.repository.NoteRepository
import com.example.notesapp.data.local.NoteDatabase
import com.example.notesapp.data.repository.NoteRepositoryImpl
import com.example.notesapp.domain.Note
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.example.notesapp.presentation.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule= module {
    single { createDataStore(androidContext()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            NoteDatabase::class.java,
            NoteDatabase.NAME
        ).build()
    }
    single<NoteRepository>{
        NoteRepositoryImpl(dao= get<NoteDatabase>().dao)
    }
    viewModelOf(::NoteViewModel)
}