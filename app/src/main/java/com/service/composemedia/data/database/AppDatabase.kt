package com.service.composemedia.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.service.composemedia.data.database.dao.SongsInfoDao
import com.service.composemedia.data.database.dao.UserInfoDao
import com.service.composemedia.data.database.entity.SongsInfoEntity
import com.service.composemedia.data.database.entity.UserInfoEntity
/*
autoMigrations = [AutoMigration(from = 1, to = 2)] to migrate database if any db schema getting changed
AutoMigration(from = 1, to = 2, spec = AppDatabase.Migration1To2::class)
@RenameColumn(tableName = "table_userinfo", fromColumnName = "user_address", toColumnName = "user_detailed")
    class Migration1To2:AutoMigrationSpec
    for migration spec
 */

@Database(entities = [UserInfoEntity::class,SongsInfoEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase :RoomDatabase(){
    abstract fun provideUserInfoDao(): UserInfoDao
    abstract fun provideSongsInfoDao(): SongsInfoDao
}