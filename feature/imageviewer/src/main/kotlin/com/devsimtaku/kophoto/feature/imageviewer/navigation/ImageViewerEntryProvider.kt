package com.devsimtaku.kophoto.feature.imageviewer.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.feature.imageviewer.ImageViewerScreen

fun EntryProviderScope<NavKey>.imageViewerEntry(
    onBackClick: () -> Unit
) {
    entry<ImageViewerNavKey> { key ->
        ImageViewerScreen(
            imageUrl = key.imageUrl,
            title = key.title,
            onBackClick = onBackClick
        )
    }
}
