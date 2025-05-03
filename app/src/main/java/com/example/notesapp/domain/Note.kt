package com.example.notesapp.domain

import android.net.Uri

data class Note(
    val id: Int?= null,
    val title: String,
    val description: String,
    val uri: Uri?,
    val isFavourited: Boolean
    )
