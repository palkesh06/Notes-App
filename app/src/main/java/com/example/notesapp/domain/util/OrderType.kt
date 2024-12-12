package com.example.notesapp.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}