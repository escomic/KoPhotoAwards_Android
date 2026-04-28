package com.devsimtaku.kophoto.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devsimtaku.kophoto.core.data.db.dao.BookmarkDao
import com.devsimtaku.kophoto.core.data.db.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1, exportSchema = false)
abstract class KoPhotoDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}
