package com.bronski.android.runnertracker.di.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.bronski.android.runnertracker.core.data.room.RunningDatabase
import com.bronski.android.runnertracker.core.utils.Constants.KEY_FIRST_TIME_TOGGLE
import com.bronski.android.runnertracker.core.utils.Constants.KEY_NAME
import com.bronski.android.runnertracker.core.utils.Constants.KEY_WEIGHT
import com.bronski.android.runnertracker.core.utils.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideRunningDataBase(
        @ApplicationContext appContext: Context,
    ) = Room.databaseBuilder(
        appContext,
        RunningDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideRunDao(runningDb: RunningDatabase) = runningDb.getRunDao()

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext appContext: Context
    ) = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideName(sharedPreferences: SharedPreferences) =
        sharedPreferences.getString(KEY_NAME, "") ?: ""

    @Provides
    @Singleton
    fun provideWeight(sharedPreferences: SharedPreferences) =
        sharedPreferences.getFloat(KEY_WEIGHT, 80f)

    @Provides
    @Singleton
    fun provideFirstTimeToggle(sharedPreferences: SharedPreferences) =
        sharedPreferences.getBoolean(KEY_FIRST_TIME_TOGGLE, true)

    companion object {
        private const val DATABASE_NAME = "running_db"
    }

}