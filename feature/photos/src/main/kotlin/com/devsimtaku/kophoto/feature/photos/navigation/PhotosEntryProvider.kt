package com.devsimtaku.kophoto.feature.photos.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.feature.photos.PhotosScreen

fun EntryProviderScope<NavKey>.photosEntry(
    onPhotoClick: (PhotoDetail) -> Unit
) {
    entry<PhotosNavKey> {
        PhotosScreen(
            onPhotoClick = onPhotoClick
        )
    }
}
