package com.example.core.navigation.routes

import kotlinx.serialization.Serializable

interface NavRoutes {
    @Serializable
    data object HomeRoute
    @Serializable
    data class NoteDetailRoute(val saveAsFavorite:Boolean= false)
    @Serializable
    data object FavoritedNotesRoute:NavRoutes
}