package com.devsimtaku.kophoto.feature.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.feature.home.HomeScreen

fun EntryProviderScope<NavKey>.homeEntry(
    onSearchClick: () -> Unit,
    onPhotoClick: (PhotoDetail) -> Unit
) {
    entry<HomeNavKey> {
        HomeScreen(
            onSearchClick = onSearchClick,
            onPhotoClick = onPhotoClick
        )
    }
}
