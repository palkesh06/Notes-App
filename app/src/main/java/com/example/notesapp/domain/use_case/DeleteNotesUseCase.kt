package com.example.notesapp.domain.use_case

import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.domain.model.NotesModel

class DeleteNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(note: NotesModel) = repository.delete(note)
}