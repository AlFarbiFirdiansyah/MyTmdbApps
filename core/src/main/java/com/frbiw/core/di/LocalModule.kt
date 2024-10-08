package com.frbiw.core.di

import android.content.Context
import androidx.room.Room
import com.frbiw.core.data.source.local.room.MovieDao
import com.frbiw.core.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class LocalModule{
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context:Context): MovieDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context, MovieDatabase::class.java, "favorite_movie.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }


    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao =
        movieDatabase.movieDao()
}

