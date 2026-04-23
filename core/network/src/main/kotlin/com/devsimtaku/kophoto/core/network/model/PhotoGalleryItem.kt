package com.devsimtaku.kophoto.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoGalleryItem(
    @SerialName("galContentId")
    val galContentId: String,
    @SerialName("galContentTypeId")
    val galContentTypeId: String,
    @SerialName("galTitle")
    val galTitle: String,
    @SerialName("galWebImageUrl")
    val galWebImageUrl: String,
    @SerialName("galCreatedtime")
    val galCreatedtime: String,
    @SerialName("galModifiedtime")
    val galModifiedtime: String,
    @SerialName("galPhotographyMonth")
    val galPhotographyMonth: String? = null,
    @SerialName("galPhotographyLocation")
    val galPhotographyLocation: String? = null,
    @SerialName("galPhotographer")
    val galPhotographer: String? = null,
    @SerialName("galSearchKeyword")
    val galSearchKeyword: String? = null
)
