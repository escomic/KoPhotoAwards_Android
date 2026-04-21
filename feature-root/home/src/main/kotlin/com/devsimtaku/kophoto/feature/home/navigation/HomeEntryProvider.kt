package com.devsimtaku.kophoto.feature.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.feature.home.HomeScreen

fun EntryProviderScope<NavKey>.homeEntry(
    onPhotoClick: (String) -> Unit
) {
    entry<HomeNavKey> {
        HomeScreen(
            onPhotoClick = onPhotoClick
        )
    }
}
