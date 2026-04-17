package com.devsimtaku.kophoto.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotoGalleryItem(
    val galContentId: String,
    val galContentTypeId: String,
    val galTitle: String,
    val galWebImageUrl: String,
    val galCreatedtime: String,
    val galModifiedtime: String,
    val galPhotographyMonth: String? = null,
    val galPhotographyLocation: String? = null,
    val galPhotographer: String? = null,
    val galSearchKeyword: String? = null
)
