package com.example.notesapp.presentation.add_edit_notes

sealed class AddEditNoteUIEvent {
    data class ShowSnackbar(val message: String) : AddEditNoteUIEvent()
    object SaveNote : AddEditNoteUIEvent()
}
