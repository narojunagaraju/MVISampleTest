package com.nnaroju.mvisample.core.di

import android.app.Application
import androidx.room.Room
import com.nnaroju.mvisample.core.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDb(application: Application): TodoDatabase {
        return Room.databaseBuilder(
            application,
            TodoDatabase::class.java,
            "notes_database.db",
        ).build()
    }

    /* @Provides
     @Singleton
     fun provideNoteRepository(notesDatabase: TodoDatabase): NotesRepository {
         return NotesRepositoryImpl(notesDatabase)
     }

     @Provides
     @Singleton
     fun provideGetAllNotesUseCase(notesRepository: NotesRepository): GetAllNotes {
         return GetAllNotes(notesRepository)
     }

     @Provides
     @Singleton
     fun provideDeleteNotesUseCase(notesRepository: NotesRepository): DeleteNotes {
         return DeleteNotes(notesRepository)
     }
 */
}