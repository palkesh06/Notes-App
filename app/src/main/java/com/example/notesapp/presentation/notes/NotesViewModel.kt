package com.example.notesapp.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.model.NotesModel
import com.example.notesapp.domain.use_case.AddNoteUseCase
import com.example.notesapp.domain.use_case.DeleteNoteUseCase
import com.example.notesapp.domain.use_case.GetNotesUseCase
import com.example.notesapp.domain.util.NotesOrder
import com.example.notesapp.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase : GetNotesUseCase,
    private val deleteNotesUseCase : DeleteNoteUseCase,
    private val addNotesUseCase : AddNoteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var fetchNotesJob: Job? = null
    private var recentlyDeletedNote: NotesModel? = null

    init {
        fetchNotes(NotesOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {
                if(state.value.notesOrders::class == event.noteOrder::class &&
                        state.value.notesOrders.order == event.noteOrder.order){
                    return
                }
                fetchNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNotesUseCase.invoke(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    addNotesUseCase.invoke(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
    private fun fetchNotes(noteOrder: NotesOrder) {
        fetchNotesJob?.cancel()
        fetchNotesJob = getNotesUseCase.invoke(noteOrder).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                notesOrders = noteOrder
            )

        }.launchIn(viewModelScope)
    }
}