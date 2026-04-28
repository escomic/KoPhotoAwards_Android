package com.devsimtaku.kophoto.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val contentId: String,
    val imageUrl: String,
    val title: String,
    val location: String,
    val filmDay: String,
    val photographer: String,
    val keyword: String,
    val description: String,
    val bookmarkedAt: Long = System.currentTimeMillis()
)

fun BookmarkEntity.toDomain(): PhotoDetail = PhotoDetail(
    contentId = contentId,
    imageUrl = imageUrl,
    title = title,
    location = location,
    filmDay = filmDay,
    photographer = photographer,
    keyword = keyword,
    description = description
)

fun PhotoDetail.toEntity(): BookmarkEntity = BookmarkEntity(
    contentId = contentId,
    imageUrl = imageUrl,
    title = title,
    location = location,
    filmDay = filmDay,
    photographer = photographer,
    keyword = keyword,
    description = description
)
