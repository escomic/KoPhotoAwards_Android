package com.devsimtaku.kophoto.core.domain.model

data class PhotoGallery(
    val id: String,
    val title: String,
    val imageUrl: String,
    val photographer: String?,
    val location: String?,
    val createdTime: String?,
    val searchKeyword: String?,
)
