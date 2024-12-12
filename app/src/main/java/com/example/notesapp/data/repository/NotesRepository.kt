package com.example.notesapp.data.repository

import com.example.notesapp.domain.model.NotesModel
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insert(note: NotesModel)

    suspend fun delete(note: NotesModel)

    suspend fun getNote(id: Int): NotesModel?

    fun getNotes(): Flow<List<NotesModel>>
}