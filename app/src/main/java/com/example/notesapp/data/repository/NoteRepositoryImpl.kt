package com.example.notesapp.data.repository

import com.example.notesapp.data.local.dao.NoteDao
import com.example.notesapp.data.mappers.toNote
import com.example.notesapp.data.mappers.toNoteEntity
import com.example.notesapp.domain.Note
import com.example.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository{
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
            .map { notes->
                notes.map {
                    it.toNote()
                }
            }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.toNote()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.toNoteEntity())
    }

    override suspend fun favoriteNote(id: Int) {
        dao.favoriteNote(id)
    }

    override fun getFavoritedNotes(): Flow<List<Note>> {
        return dao.getFavoritedNotes().map { notes->
            notes.map {
                it.toNote()
            }
        }
    }
}