package com.example.notesapp.injection

import android.app.Application
import androidx.room.Room
import com.example.notesapp.data.data_source.DatabaseConst
import com.example.notesapp.data.data_source.NoteDao
import com.example.notesapp.data.data_source.NoteDatabase
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.domain.repository.NotesRepositoryImpl
import com.example.notesapp.domain.use_case.AddNoteUseCase
import com.example.notesapp.domain.use_case.DeleteNoteUseCase
import com.example.notesapp.domain.use_case.GetNoteUseCase
import com.example.notesapp.domain.use_case.GetNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase =
        Room.databaseBuilder(app, NoteDatabase::class.java, DatabaseConst.NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase): NoteDao = db.noteDao()

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NotesRepository = NotesRepositoryImpl(noteDao)

    @Provides
    fun provideGetNotesUseCase(noteRepository: NotesRepository): GetNotesUseCase = GetNotesUseCase(noteRepository)

    @Provides
    fun provideDeleteNoteUseCase(noteRepository: NotesRepository): DeleteNoteUseCase = DeleteNoteUseCase(noteRepository)

    @Provides
    fun provideAddNoteUseCase(noteRepository: NotesRepository): AddNoteUseCase = AddNoteUseCase(noteRepository)

    @Provides
    fun provideGetNoteUsaCase(noteRepository: NotesRepository): GetNoteUseCase = GetNoteUseCase(noteRepository)
}