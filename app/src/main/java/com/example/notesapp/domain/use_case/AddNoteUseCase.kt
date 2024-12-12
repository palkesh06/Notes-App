package com.example.notesapp.domain.use_case

import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.domain.model.InvalidNoteException
import com.example.notesapp.domain.model.NotesModel

class AddNoteUseCase(private val repository: NotesRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: NotesModel) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of note can't be empty!")
        }

        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of note can't be empty!")
        }

        repository.insert(note)
    }
}