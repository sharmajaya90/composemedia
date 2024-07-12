package com.service.composemedia.di.module

import android.content.Context
import androidx.room.Room
import com.service.composemedia.data.database.AppDatabase
import com.service.composemedia.data.database.dao.SongsInfoDao
import com.service.composemedia.data.database.dao.UserInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "compose_media_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    @Provides
    fun provideUserInfoDao(database: AppDatabase): UserInfoDao {
        return database.provideUserInfoDao()
    }

    @Provides
    fun provideSongsInfoDao(database: AppDatabase): SongsInfoDao {
        return database.provideSongsInfoDao()
    }
}