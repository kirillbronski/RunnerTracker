package com.bronski.android.runnertracker.di.modules

import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.main.repository.IMainRepository
import com.bronski.android.runnertracker.main.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun providesMainRepository(runDao: RunDao): IMainRepository = MainRepositoryImpl(runDao)


}