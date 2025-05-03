package com.example.notesapp.presentation

import android.content.Context
import android.media.Image
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.Note
import com.example.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class NoteViewModel(
    private val repository: NoteRepository,
    private val dataStore: DataStore<Preferences>,
): ViewModel() {

    private val _uriState= MutableStateFlow(ImageState())
    val uriState: StateFlow<ImageState> = _uriState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ImageState()
        )

    private val _state= MutableStateFlow(NoteState())
    val state= _state
        .onStart { getAllNotes() }
        .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NoteState()
    )

    private val _favoritedNotes= MutableStateFlow(emptyList<Note>())
    val favoritedNotes= _favoritedNotes
        .onStart { getFavoritedNotes() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

   val isDarktheme =  dataStore.data

   fun onAction(actions: NoteActions){
       when(actions){
           is NoteActions.onSaveImage-> {
               if(actions.uri!=null){
                   saveImage(
                       uri = actions.uri,
                       context= actions.context
                   )
               }
           }
           is NoteActions.onDeleteNote-> {deleteNote(actions.note)}
           is NoteActions.onAddNote-> {
               insertNote(actions.note)
               clearData()
           }
           is NoteActions.onUpdateNote->{
               updateNote(actions.note)
               clearData()
           }
           is NoteActions.onFavoriteNote-> {
               actions.id?.let { favoriteNote(it) }
           }
           is NoteActions.onTitleChange->{ changeTitle(actions.title)}
           is NoteActions.onDescriptionChange->{ changeDescription(actions.description)}
           is NoteActions.onSetupToNoteScreen-> {
               actions.id?.let {
                   getNoteById(it)
               }
           }
           is NoteActions.onClearData-> {clearData()}
           is NoteActions.onLoadFavoritedNotes-> {getFavoritedNotes()}
           is NoteActions.onSavePreferences-> {saveTheme(actions.darkTheme)}
       }
   }

    private fun saveImage(uri: String, context: Context){
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val bytes= context.contentResolver.openInputStream(uri.toUri())?.use{ inputStream->
                inputStream.readBytes()
            }?: byteArrayOf()
            val fileName= UUID.randomUUID().toString()
            val mimeType= context.contentResolver.getType(uri.toUri())?:""
            val extension= MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            val actualFileName= "${fileName}.${extension}"
            val image= File(context.filesDir, actualFileName )
            FileOutputStream(image).use {
                it.write(bytes)
            }
            _uriState.update {
                it.copy(
                    imageUri = Uri.fromFile(image).toString()
                )
            }
            _state.update{
                it.copy(isLoading = false)
            }
        }
    }


   private fun getAllNotes(){
       _state.update {
           it.copy(isLoading = true)
       }
       viewModelScope.launch(Dispatchers.IO) {
           repository.getAllNotes().collectLatest { notes->
               _state.update {
                   it.copy(
                       notes = notes,
                       isLoading = false
                   )
               }
           }
       }
   }

    private fun getNoteById(id:Int){
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val note= repository.getNoteById(id)
            _state.update {
                it.copy(
                    isLoading = false,
                    selectedNote = note,
                    title = note?.title?:"",
                    description = note?.description?:""
                    )
            }
            }
        }

    private fun insertNote(note: Note){
        _state.update {
            it.copy(selectedNote = null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }

    private fun updateNote(note: Note){
        _state.update {
            it.copy(selectedNote = null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    private fun favoriteNote(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.favoriteNote(id)
        }
    }

    private fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    private fun changeTitle(title: String){
        _state.update {
            it.copy(
                title = title
            )
        }
    }
    private fun changeDescription(description: String){
        _state.update {
            it.copy(
                description = description
            )
        }
    }

    private fun getFavoritedNotes(){
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoritedNotes().collectLatest { notes->
                _favoritedNotes.update { notes }
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun clearData(){
        _state.update {
            it.copy(
                title = "",
                description = "",
                selectedNote = null
            )
        }
    }

    private fun saveTheme(isDarkTheme: Boolean ){
        viewModelScope.launch {
            dataStore.edit {
                val darkTheme= booleanPreferencesKey("darkTheme")
                it[darkTheme]= isDarkTheme
            }
        }
    }
}