package com.bronski.android.runnertracker.di.modules

import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.run.ui.repository.IRunRepository
import com.bronski.android.runnertracker.run.ui.repository.RunRepositoryImpl
import com.bronski.android.runnertracker.statistics.repository.IStatisticsRepository
import com.bronski.android.runnertracker.statistics.repository.StatisticsRepositoryImpl
import com.bronski.android.runnertracker.tracking.repository.ITrackingRepository
import com.bronski.android.runnertracker.tracking.repository.TrackingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun providesTrackingRepository(runDao: RunDao): ITrackingRepository =
        TrackingRepositoryImpl(runDao)

    @Provides
    @Singleton
    fun providesRunRepository(runDao: RunDao): IRunRepository =
        RunRepositoryImpl(runDao)

    @Provides
    @Singleton
    fun providesStatisticsRepository(runDao: RunDao): IStatisticsRepository =
        StatisticsRepositoryImpl(runDao)

}