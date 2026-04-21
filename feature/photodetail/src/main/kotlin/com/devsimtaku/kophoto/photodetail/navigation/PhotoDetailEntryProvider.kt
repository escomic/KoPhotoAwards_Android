package com.devsimtaku.kophoto.photodetail.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.photodetail.PhotoDetailScreen

fun EntryProviderScope<NavKey>.photoDetailEntry(
    onBackClick: () -> Unit
) {
    entry<PhotoDetailNavKey> { key ->
        PhotoDetailScreen(
            item = key.item,
            onBackClick = onBackClick
        )
    }
}
