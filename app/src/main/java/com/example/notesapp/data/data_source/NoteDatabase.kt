package com.example.notesapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notesapp.domain.model.NotesModel


@TypeConverters(Converters::class)
@Database(
    entities = [NotesModel::class],
    version = DatabaseConst.VERSION,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}