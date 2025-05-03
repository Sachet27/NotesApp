package com.example.notesapp.domain.repository

import com.example.notesapp.domain.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun favoriteNote(id: Int)
    fun getFavoritedNotes(): Flow<List<Note>>
}