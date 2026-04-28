package com.devsimtaku.kophoto.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devsimtaku.kophoto.core.data.db.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY bookmarkedAt DESC")
    fun getBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity): Long

    @Query("DELETE FROM bookmarks WHERE contentId = :contentId")
    suspend fun deleteBookmark(contentId: String): Int

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE contentId = :contentId)")
    fun isBookmarked(contentId: String): Flow<Boolean>
}
