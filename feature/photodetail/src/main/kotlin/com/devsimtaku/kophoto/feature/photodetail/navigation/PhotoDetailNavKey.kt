package com.devsimtaku.kophoto.feature.photodetail.navigation

import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDetailNavKey(
    val item: PhotoDetail
) : NavKey {
    companion object {
        const val ARGS_KEY_ITEM = "item"
    }
}
