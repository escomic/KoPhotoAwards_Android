package com.devsimtaku.kophoto.feature.search.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.feature.search.SearchScreen

fun EntryProviderScope<NavKey>.searchEntry(
    onBackClick: () -> Unit,
    onPhotoClick: (com.devsimtaku.kophoto.core.domain.model.PhotoDetail) -> Unit
) {
    entry<SearchNavKey> { key ->
        SearchScreen(
            initialQuery = key.query,
            onBackClick = onBackClick,
            onPhotoClick = onPhotoClick
        )
    }
}
