package com.devsimtaku.kophoto.core.domain.model

data class PhotoAward(
    val id: String,
    val title: String,
    val photographer: String?,
    val thumbnail: String?,
    val original: String?,
    val description: String?,
    val keyword: String?,
    val date: String?,
)
