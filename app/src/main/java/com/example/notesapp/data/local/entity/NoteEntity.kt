package com.example.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    val title: String,
    val description: String,
    val imageUri: String?,
    val isFavorited: Boolean
)