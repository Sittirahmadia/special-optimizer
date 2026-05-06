package com.cyberbeast.optimizer.di

import android.content.Context
import androidx.room.Room
import com.cyberbeast.optimizer.data.local.AppDatabase
import com.cyberbeast.optimizer.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideOptimizerStringDao(database: AppDatabase) = database.optimizerStringDao()

    @Provides
    @Singleton
    fun provideProfileDao(database: AppDatabase) = database.profileDao()
}
