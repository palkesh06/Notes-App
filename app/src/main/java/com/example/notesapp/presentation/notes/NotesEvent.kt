package com.example.notesapp.presentation.notes

import com.example.notesapp.domain.model.NotesModel
import com.example.notesapp.domain.util.NotesOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NotesOrder) : NotesEvent()
    data class DeleteNote(val note: NotesModel) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}