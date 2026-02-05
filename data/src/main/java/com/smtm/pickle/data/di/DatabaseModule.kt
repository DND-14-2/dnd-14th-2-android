package com.smtm.pickle.data.di

import android.content.Context
import androidx.room.Room
import com.smtm.pickle.data.source.local.database.PickleDatabase
import com.smtm.pickle.data.source.local.database.dao.LedgerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): PickleDatabase {
        return Room.databaseBuilder(
            context,
            PickleDatabase::class.java,
            "pickle_database"
        ).build()
    }

    @Provides
    fun provideLedgerDao(database: PickleDatabase): LedgerDao {
        return database.ledgerDao()
    }
}
