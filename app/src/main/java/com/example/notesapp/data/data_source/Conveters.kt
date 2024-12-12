package com.example.notesapp.data.data_source


import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromDate(time: Date): Long = time.time

    @TypeConverter
    fun toDate(time: Long): Date = Date(time)
}