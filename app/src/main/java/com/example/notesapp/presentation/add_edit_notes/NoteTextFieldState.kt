package com.example.notesapp.presentation.add_edit_notes

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val hintVisible: Boolean = true
)
