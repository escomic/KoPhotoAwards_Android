package com.devsimtaku.kophoto.core.data.di

import android.content.Context
import androidx.room.Room
import com.devsimtaku.kophoto.core.data.db.KoPhotoDatabase
import com.devsimtaku.kophoto.core.data.db.dao.BookmarkDao
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
    fun provideKoPhotoDatabase(
        @ApplicationContext context: Context
    ): KoPhotoDatabase = Room.databaseBuilder(
        context,
        KoPhotoDatabase::class.java,
        "kophoto.db"
    ).build()

    @Provides
    @Singleton
    fun provideBookmarkDao(
        database: KoPhotoDatabase
    ): BookmarkDao = database.bookmarkDao()
}
