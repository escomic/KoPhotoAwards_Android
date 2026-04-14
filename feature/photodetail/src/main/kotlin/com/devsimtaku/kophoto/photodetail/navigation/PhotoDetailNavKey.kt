package com.devsimtaku.kophoto.photodetail.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDetailNavKey(val id: String) : NavKey {
}