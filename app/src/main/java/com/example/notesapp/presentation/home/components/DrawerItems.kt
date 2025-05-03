package com.example.notesapp.presentation.home.components

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import com.example.notesapp.R

enum class DrawerItems {
    HOME, REMINDER, FAVORITES, SETTINGS;
    companion object {
        val list= listOf(HOME, REMINDER, FAVORITES, SETTINGS)
        fun DrawerItems.toSelectionString(): String{
            return when(this){
                HOME-> "Home"
                REMINDER-> "Reminder"
                FAVORITES-> "Favorites"
                SETTINGS-> "Dark Theme"
            }
        }
        fun DrawerItems.toIcon(): Int{
            return when(this){
                HOME-> R.drawable.home_icon
                REMINDER-> R.drawable.reminder_icon
                FAVORITES-> R.drawable.star_outline
                SETTINGS-> R.drawable.settings_icon
            }
        }
            }
        }


