package com.example.notesapp.data.data_source

import androidx.room.*
import com.example.notesapp.domain.model.NotesModel
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NotesModel)

    @Delete
    suspend fun delete(note: NotesModel)

    @Query("SELECT * FROM ${DatabaseConst.NOTE_TABLE} WHERE id = :id")
    suspend fun getNote(id: Int): NotesModel?

    @Query("SELECT * FROM ${DatabaseConst.NOTE_TABLE} ORDER BY timeStamp DESC")
    fun getNotes(): Flow<List<NotesModel>>
}