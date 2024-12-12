package com.example.notesapp.domain.use_case

import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.domain.model.NotesModel
import com.example.notesapp.domain.util.NotesOrder
import com.example.notesapp.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NotesRepository
) {
    operator fun invoke(order: NotesOrder = NotesOrder.Date(OrderType.Descending)): Flow<List<NotesModel>> {
        return repository.getNotes().map { notes ->
            order is NotesOrder.Date
            when (order) {

                is NotesOrder.Title -> {
                    when (order.order) {
                        OrderType.Ascending -> notes.sortedBy { it.title.lowercase() }
                        OrderType.Descending -> notes.sortedByDescending { it.title.lowercase() }
                    }
                }

                is NotesOrder.Date -> {
                    when (order.order) {
                        OrderType.Ascending -> notes.sortedBy { it.timeStamp.time }
                        OrderType.Descending -> notes.sortedByDescending { it.timeStamp.time }
                    }
                }

                is NotesOrder.Color -> {
                    when (order.order) {
                        OrderType.Ascending -> notes.sortedBy { it.color }
                        OrderType.Descending -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}