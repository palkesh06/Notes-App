package com.example.notesapp.domain.repository

import com.example.notesapp.data.data_source.NoteDao
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.domain.model.NotesModel

class NotesRepositoryImpl (
    private val dao: NoteDao
) : NotesRepository  {
    override suspend fun insert(note: NotesModel) = dao.insert(note)

    override suspend fun delete(note: NotesModel)  = dao.delete(note)

    override suspend fun getNote(id: Int) = dao.getNote(id)

    override fun getNotes() = dao.getNotes()
}