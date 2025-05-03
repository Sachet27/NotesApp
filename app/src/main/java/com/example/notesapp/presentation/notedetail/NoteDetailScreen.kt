package com.example.notesapp.presentation.notedetail

import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.notesapp.R
import com.example.notesapp.domain.Note
import com.example.notesapp.presentation.NoteActions
import com.example.notesapp.presentation.NoteState
import com.example.core.mappers.toAlarmItem
import com.example.core.mappers.toLocalReadableTime
import com.example.notesapp.presentation.ImageState
import com.example.notesapp.presentation.notedetail.components.NoteEditTopBar
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.reminder.domain.models.AlarmItem
import com.example.reminder.presentation.ReminderActions
import com.example.reminder.presentation.TimePickerDialog
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    modifier: Modifier= Modifier,
    uriState: ImageState,
    state: NoteState,
    onAction: (NoteActions)->Unit,
    saveAsFavorites: Boolean= false,
    onBackNav: ()-> Unit,
    onPushNotification: (AlarmItem)->Unit,
    onCancelReminder: (AlarmItem)-> Unit
) {
    val context= LocalContext.current
    //db ma remindered ko lagi xuttai boolean hala ani yesma reflect gara
    var alarmItem by remember { mutableStateOf<AlarmItem?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val photoPicker= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri->
        onAction(NoteActions.onSaveImage(uri.toString(), context))
    }

    BackHandler {
        alarmItem= null
        onBackNav()
        onAction(NoteActions.onClearData)
    }
    Scaffold(
        topBar = {
            NoteEditTopBar(
                title = if (state.selectedNote == null) "Add Note" else "Edit Note",
                onBackClick = {
                    alarmItem= null
                    onBackNav()
                    onAction(NoteActions.onClearData)
                },
                onSaveNote = {
                    if(state.selectedNote==null){
                        onAction(NoteActions.onAddNote(
                            Note(
                                id = null,
                                title = state.title,
                                description = state.description,
                                uri = uriState.imageUri?.toUri(),
                                isFavourited = saveAsFavorites
                            )
                        ))
                    } else{
                        onAction(NoteActions.onUpdateNote(
                            state.selectedNote.copy(
                                title = state.title,
                                description = state.description,
                                uri = uriState.imageUri?.toUri()
                            )
                        ))
                    }
                    if(alarmItem!=null) {
                        Log.d("Yeet", alarmItem?.message.toString())
                        onPushNotification(
                            alarmItem?: AlarmItem(LocalDateTime.now(),"Currently null")
                        )
                        Toast.makeText(context, "Reminder created for ${alarmItem?.time?.toLocalReadableTime()}", Toast.LENGTH_SHORT).show()
                    }
                    onBackNav()
                },
                onSetReminder = {
                    if(alarmItem!=null){
                        onCancelReminder(alarmItem?:AlarmItem(LocalDateTime.now(), "Null reminder"))
                        Toast.makeText(context, "Cancelled reminder", Toast.LENGTH_SHORT).show()
                        alarmItem=null
                    } else {
                        showDialog = true
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    photoPicker.launch("image/*")
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_insert_photo_24),
                    contentDescription = null
                )
            }
        }

    ) { padding->
        if (showDialog){
            TimePickerDialog(
                onDismiss = {
                    showDialog= false
                },
                onConfirm = {time->
                        alarmItem= state.selectedNote?.toAlarmItem(time)?: Note(
                            title = state.title, description = state.description,
                            isFavourited = saveAsFavorites,
                            uri = null
                        ).toAlarmItem(time)
                        showDialog= false
                }
            )
        }
        if(state.isLoading){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
              CircularProgressIndicator()
            }
        } else{
            Column(
                modifier= modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = state.title,
                    onValueChange = {onAction(NoteActions.onTitleChange(it))},
                    placeholder = { Text(text= "Enter the title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background
                    )
                )
                HorizontalDivider()
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = state.description,
                    onValueChange = {onAction(NoteActions.onDescriptionChange(it))},
                    placeholder = { Text(text= "Enter the description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background
                    )
                )
                AsyncImage(
                    model = uriState.imageUri?: state.selectedNote?.uri,
                    contentScale = ContentScale.Fit,
                    contentDescription=null
                )

            }

        }
    }
}

