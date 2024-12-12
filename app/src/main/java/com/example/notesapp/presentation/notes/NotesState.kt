package com.example.notesapp.presentation.notes

import com.example.notesapp.domain.model.NotesModel
import com.example.notesapp.domain.util.NotesOrder
import com.example.notesapp.domain.util.OrderType

data class NotesState (
    val notes : List<NotesModel> = emptyList(),
    val notesOrders : NotesOrder = NotesOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
