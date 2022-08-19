package com.bronski.android.runnertracker.di.modules

import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.main.repository.ITrackingRepository
import com.bronski.android.runnertracker.tracking.repository.TrackingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun providesTrackingRepository(runDao: RunDao): ITrackingRepository = TrackingRepositoryImpl(runDao)

}