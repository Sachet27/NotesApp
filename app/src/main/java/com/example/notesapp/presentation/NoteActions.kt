package com.example.notesapp.presentation

import android.content.Context
import com.example.notesapp.domain.Note

interface NoteActions{
    data class onSaveImage(val uri: String?, val context: Context): NoteActions
    data class onAddNote(val note: Note): NoteActions
    data class onUpdateNote(val note: Note): NoteActions
    data class onDeleteNote(val note: Note,val context: Context): NoteActions
    data class onFavoriteNote(val id: Int?): NoteActions
    data object onClearData: NoteActions
    data class onSetupToNoteScreen(val id: Int?): NoteActions
    data class onTitleChange(val title: String):NoteActions
    data class onDescriptionChange(val description: String):NoteActions
    data object onLoadFavoritedNotes: NoteActions
    data class onSavePreferences(val darkTheme: Boolean): NoteActions
}
