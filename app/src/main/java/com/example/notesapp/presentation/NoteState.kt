package com.example.notesapp.presentation

import com.example.notesapp.domain.Note

data class NoteState(
    val isLoading:Boolean = false,
    val notes: List<Note> = emptyList(),
    val selectedNote: Note?= null,
    val title: String = "",
    val description: String = ""
)
