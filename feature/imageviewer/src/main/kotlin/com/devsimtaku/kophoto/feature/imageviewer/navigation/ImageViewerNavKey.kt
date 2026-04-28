package com.devsimtaku.kophoto.feature.imageviewer.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class ImageViewerNavKey(
    val imageUrl: String,
    val title: String? = null
) : NavKey
