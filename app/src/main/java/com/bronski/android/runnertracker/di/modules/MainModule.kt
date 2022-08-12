package com.bronski.android.runnertracker.di.modules

import android.content.Context
import androidx.room.Room
import com.bronski.android.runnertracker.core.data.room.RunningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun provideRunningDataBase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        RunningDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    fun provideRunDao(runningDb: RunningDatabase) = runningDb.getRunDao()

    companion object {
        private const val DATABASE_NAME = "running_db"
    }

}