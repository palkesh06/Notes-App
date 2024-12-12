package com.example.notesapp.domain.util

sealed class NotesOrder(val order: OrderType)  {
    class Title(order: OrderType) : NotesOrder(order)
    class Date(order: OrderType) : NotesOrder(order)
    class Color(order: OrderType) : NotesOrder(order)

    fun copy(order: OrderType ) = when(this) {
        is Title -> Title(order)
        is Date -> Date(order)
        is Color -> Color(order)
    }
}