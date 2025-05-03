package com.example.notesapp.presentation.favorited


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.notesapp.R
import com.example.notesapp.domain.Note
import com.example.notesapp.presentation.NoteActions
import com.example.notesapp.presentation.NoteState
import com.example.notesapp.presentation.home.components.NavDrawer
import com.example.notesapp.presentation.home.components.NavigationItems
import com.example.notesapp.presentation.home.components.NoteCard
import com.example.notesapp.presentation.home.components.SideDrawerNavItems
import com.example.notesapp.presentation.home.components.TopBar
import com.example.notesapp.presentation.notedetail.components.NoteEditTopBar
import com.example.notesapp.ui.theme.NotesAppTheme
import kotlinx.coroutines.launch

@Composable
fun FavoritedNotesScreen(
    notes: List<Note>,
    onBackNav: ()->Unit,
    onAction: (NoteActions)-> Unit,
    onNavigateToNoteScreen: ()->Unit
) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                NoteEditTopBar(
                    title = "Favorited Notes",
                    onBackClick = { onBackNav() },

                    onSaveNote = {onBackNav()},
                    onSetReminder = {
                    }
                  )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onAction(NoteActions.onSetupToNoteScreen(null))
                        onNavigateToNoteScreen()
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Create,
                        contentDescription = null,
                    )
                }
            }

        ) { padding->
            if(notes.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement= Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.emptynotes),
                        contentDescription = null,
                        modifier = Modifier.size(300.dp),
                        alpha = 0.8f
                    )
                    Text(
                        text = "No Notes Favorited Yet.",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp
                    )

                }
            }
            else{
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .padding(start = 12.dp, end= 12.dp, bottom= 16.dp, top= 110.dp),
                    columns = StaggeredGridCells.Fixed(2),
                ) {
                    items(notes){ note->
                        NoteCard(
                            note = note,
                            onNoteClick = {
                                onAction(NoteActions.onSetupToNoteScreen(note.id))
                                onNavigateToNoteScreen()
                            },
                            onNoteFavorite = { onAction(NoteActions.onFavoriteNote(note.id)) },
                            modifier = Modifier.padding(8.dp),
                            onDeleteNote = { onAction(NoteActions.onDeleteNote(note)) }
                        )
                    }
                }
            }
        }
    }

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    NotesAppTheme {
        FavoritedNotesScreen(
            notes = emptyList(),
            onBackNav = {},
            onAction = {}
        ) { }
    }
}