package com.example.notesapp.presentation

data class ImageState(
    val imageUri: String? = null,
    val isUploading: Boolean = false,
    val isUploaded:Boolean = false
)
