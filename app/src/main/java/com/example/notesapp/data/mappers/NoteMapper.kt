package com.example.notesapp.data.mappers

import androidx.core.net.toUri
import com.example.notesapp.data.local.entity.NoteEntity
import com.example.notesapp.domain.Note

fun NoteEntity.toNote(): Note{
    return Note(
        id = id,
        title = title,
        description = description,
        uri = imageUri?.toUri(),
        isFavourited = isFavorited
    )
}

fun Note.toNoteEntity(): NoteEntity{
    return NoteEntity(
        id = id,
        title = title,
        description = description,
        imageUri = uri?.toString(),
        isFavorited = isFavourited
    )
}