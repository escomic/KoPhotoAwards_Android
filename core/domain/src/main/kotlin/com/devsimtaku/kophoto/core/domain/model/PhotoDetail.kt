package com.devsimtaku.kophoto.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotoDetail(
    val contentId: String,
    val imageUrl: String,
    val title: String,
    val location: String,
    val filmDay: String,
    val photographer: String,
    val keyword: String,
    val description: String
)
