package com.example.notesapp.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.example.notesapp.R
import com.example.notesapp.presentation.NoteActions
import com.example.notesapp.presentation.NoteState
import com.example.notesapp.presentation.home.components.DrawerItems
import com.example.notesapp.presentation.home.components.NavDrawer
import com.example.notesapp.presentation.home.components.NavigationItems
import com.example.notesapp.presentation.home.components.NoteCard
import com.example.notesapp.presentation.home.components.SideDrawerNavItems
import com.example.notesapp.presentation.home.components.TopBar
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreen(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    state: NoteState,
    onAction: (NoteActions)-> Unit,
    modifier: Modifier= Modifier,
    onDrawerItemClick: (NavigationItems)->Unit,
    onNavigateToNoteScreen: ()->Unit
) {
    val context= LocalContext.current
    var hasNotificationPermission by remember {
        if(VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
            mutableStateOf(
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_GRANTED
            )
        } else{
            mutableStateOf(true)
        }
    }
    val permissionLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted->
        hasNotificationPermission= isGranted
    }
    val scope= rememberCoroutineScope()
    val drawerState= DrawerState(DrawerValue.Closed)
    NavDrawer(
        enabled = true,
        drawerState = drawerState,
        items = SideDrawerNavItems.navItemsList,
        onItemClick = {
            onDrawerItemClick(it)
            if(it.id!=DrawerItems.SETTINGS) {
                scope.launch {
                    drawerState.close()
                }
            }
        },
        onThemeToggle = onThemeToggle,
        isDarkTheme = isDarkTheme,
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = { TopBar(
                title = "My Notes",
                onSearch = {},
                onDrawerClick = {
                    scope.launch {
                        drawerState.open()
                    }
                },
            ) },
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
            if(!hasNotificationPermission){
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            if(state.isLoading){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment= Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
            else if(state.notes.isEmpty()){
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
                        text = "No Notes Yet.",
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
                    items(state.notes){ note->
                        NoteCard(
                            note = note,
                            onNoteClick = {
                                onAction(NoteActions.onSetupToNoteScreen(note.id))
                                onNavigateToNoteScreen()
                            },
                            onNoteFavorite = { onAction(NoteActions.onFavoriteNote(note.id)) },
                            modifier = Modifier.padding(8.dp),
                            onDeleteNote = {
                                onAction(NoteActions.onDeleteNote(note))
                            }
                        )
                    }
                }
            }
        }

    }
}

