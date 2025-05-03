package com.example.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.notesapp.data.local.entity.NoteEntity
import com.example.notesapp.domain.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM NoteEntity")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id= :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Query("UPDATE NoteEntity SET isFavorited= NOT isFavorited WHERE id= :id")
    suspend fun favoriteNote(id:Int)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM NoteEntity WHERE isFavorited= 1")
    fun getFavoritedNotes(): Flow<List<NoteEntity>>
}