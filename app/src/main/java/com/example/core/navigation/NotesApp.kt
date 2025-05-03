package com.example.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.core.navigation.routes.NavRoutes
import com.example.notesapp.presentation.NoteActions
import com.example.notesapp.presentation.NoteViewModel
import com.example.notesapp.presentation.favorited.FavoritedNotesScreen
import com.example.notesapp.presentation.home.HomeScreen
import com.example.notesapp.presentation.home.components.DrawerItems
import com.example.notesapp.presentation.notedetail.NoteDetailScreen
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.reminder.presentation.ReminderActions
import com.example.reminder.presentation.ReminderViewModel
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotesApp() {
    val reminderViewModel= koinViewModel<ReminderViewModel>()
    val noteViewModel= koinViewModel<NoteViewModel>()
    val isDarkTheme by noteViewModel.isDarktheme.map {
        val darkTheme= booleanPreferencesKey("darkTheme")
        it[darkTheme]?: false
    }.collectAsStateWithLifecycle(false)
    val state by noteViewModel.state.collectAsStateWithLifecycle()
    val uriState by noteViewModel.uriState.collectAsStateWithLifecycle()
    val favoritedNotes by noteViewModel.favoritedNotes.collectAsStateWithLifecycle()
    val navController= rememberNavController()
    NotesAppTheme(darkTheme = isDarkTheme) {
        NavHost(navController= navController, startDestination = NavRoutes.HomeRoute){
            composable<NavRoutes.HomeRoute>{
                HomeScreen(
                    state = state,
                    onAction = noteViewModel::onAction,
                    onDrawerItemClick = { item ->
                        when (item.id) {
                            DrawerItems.HOME -> {
                                navController.popBackStack(
                                    route = NavRoutes.HomeRoute,
                                    inclusive = false
                                )
                            }

                            DrawerItems.REMINDER -> {}
                            DrawerItems.FAVORITES -> {
                                navController.navigate(NavRoutes.FavoritedNotesRoute)
                            }

                            DrawerItems.SETTINGS -> {
                                //nothing needs to be done here
                            }
                        }
                    },
                    onNavigateToNoteScreen = {
                        navController.navigate(NavRoutes.NoteDetailRoute(false))
                    },
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = {
                        noteViewModel.onAction(NoteActions.onSavePreferences(it))
                    }
                )
            }
            composable<NavRoutes.NoteDetailRoute>{
                val saveAsFavorite= it.toRoute<NavRoutes.NoteDetailRoute>().saveAsFavorite
                NoteDetailScreen(
                    state = state,
                    saveAsFavorites = saveAsFavorite,
                    onAction = noteViewModel::onAction,
                    onBackNav = {
                        navController.popBackStack()
                    },
                    onPushNotification = {
                        reminderViewModel.onAction(ReminderActions.onPushReminder(it))
                    },
                    onCancelReminder = {
                        reminderViewModel.onAction(
                            ReminderActions.onCancelReminder(it)
                        )
                    },
                    uriState = uriState
                )
            }
            composable<NavRoutes.FavoritedNotesRoute> {
                FavoritedNotesScreen(
                    notes = favoritedNotes,
                    onBackNav = { navController.popBackStack() },
                    onAction = noteViewModel::onAction,
                    onNavigateToNoteScreen = {navController.navigate(NavRoutes.NoteDetailRoute(true))}
                )
            }
        }

    }
}