package com.example.notesapp.presentation.add_edit_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.model.InvalidNoteException
import com.example.notesapp.domain.model.NotesModel
import com.example.notesapp.domain.use_case.AddNoteUseCase
import com.example.notesapp.domain.use_case.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val getNote: GetNoteUseCase,
    private val addNote: AddNoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter some content"))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(NotesModel.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<AddEditNoteUIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    getNote(noteId)?.let { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            hintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            hintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(text = event.value)
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(hintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank())
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(text = event.value)
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(hintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank())
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        addNote(
                            NotesModel(
                                id = currentNoteId,
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                color = noteColor.value
                            )
                        )
                        _eventFlow.emit(AddEditNoteUIEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(AddEditNoteUIEvent.ShowSnackbar(e.message ?: "Unknown error"))
                    }
                }
            }
        }
    }
}