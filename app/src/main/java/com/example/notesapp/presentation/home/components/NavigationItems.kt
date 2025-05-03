package com.example.notesapp.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItems (
    val id: DrawerItems,
    val title: String,
    @DrawableRes val iconRes: Int,
)