package com.example.notesapp.presentation.notedetail.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.R
import com.example.notesapp.presentation.home.components.TopBar
import com.example.notesapp.ui.theme.NotesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditTopBar(
    title: String,
    onBackClick: ()-> Unit,
    onSaveNote: ()->Unit,
    modifier: Modifier= Modifier,
    onSetReminder:  ()->Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                modifier = Modifier.padding(16.dp),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackClick()}
            ) {
                Icon(
                    modifier = modifier.size(30.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            TextButton(
                onClick = {
                    onSaveNote()
                }
            ) {
                IconButton(
                    onClick = onSetReminder
                ){
                    Icon(
                        modifier= Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.reminder_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "Done",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )

            }
        }
    )
}

@PreviewLightDark
@Composable
private fun TopBarPreview() {
    NotesAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                NoteEditTopBar(
                    title = "Add Note",
                    onBackClick = {},
                    onSaveNote = {},
                    onSetReminder = {}
                )
            }
        ) { padding ->
            Column(Modifier.padding(padding)) { }


        }
    }
}