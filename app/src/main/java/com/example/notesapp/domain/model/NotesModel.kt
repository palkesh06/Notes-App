package com.example.notesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.data.data_source.DatabaseConst
import com.example.notesapp.presentation.ui.theme.*
import java.util.*

@Entity(tableName = DatabaseConst.NOTE_TABLE)
data class NotesModel(
    @PrimaryKey
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val color: Int = 0,
    val timeStamp: Date = Date()
){
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)
