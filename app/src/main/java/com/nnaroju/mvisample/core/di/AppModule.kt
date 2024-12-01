package com.nnaroju.mvisample.core.di

import android.app.Application
import androidx.room.Room
import com.nnaroju.mvisample.addtoitem.use_cases.InsertTodoItemUseCase
import com.nnaroju.mvisample.core.data.local.TodoDatabase
import com.nnaroju.mvisample.core.data.repository.TodoRepositoryImpl
import com.nnaroju.mvisample.core.domian.repository.TodoRepository
import com.nnaroju.mvisample.searchitems.presentation.use_cases.SearchItemsUseCase
import com.nnaroju.mvisample.todohome.use_cases.GetToDoItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

    @Provides
    @Singleton
    fun provideTodoRepository(todoDatabase: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(todoDatabase.todoDao)
    }

    @Provides
    @Singleton
    fun provideGetToDoItemsUseCase(todoRepository: TodoRepository): GetToDoItemsUseCase {
        return GetToDoItemsUseCase(todoRepository)
    }

    @Provides
    @Singleton
    fun provideInsertTodoItemUseCase(todoRepository: TodoRepository): InsertTodoItemUseCase {
        return InsertTodoItemUseCase(todoRepository)
    }

    @Provides
    @Singleton
    fun provideSearchItemUseCase(todoRepository: TodoRepository): SearchItemsUseCase {
        return SearchItemsUseCase(todoRepository)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}