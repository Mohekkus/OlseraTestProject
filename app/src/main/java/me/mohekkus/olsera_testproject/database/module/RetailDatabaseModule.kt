package me.mohekkus.olsera_testproject.database.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.mohekkus.olsera_testproject.database.dao.RetailDAO
import me.mohekkus.olsera_testproject.database.RetailDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetailDatabaseModule {
    @Provides
    fun provideRetailDAO(retailDatabase: RetailDatabase): RetailDAO =
        retailDatabase.retailDAO()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RetailDatabase {
        return Room.databaseBuilder(
            appContext,
            RetailDatabase::class.java,
            "RssReader"
        ).build()
    }
}