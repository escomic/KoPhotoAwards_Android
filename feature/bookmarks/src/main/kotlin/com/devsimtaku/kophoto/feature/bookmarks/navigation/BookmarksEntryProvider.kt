package com.devsimtaku.kophoto.feature.bookmarks.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.feature.bookmarks.BookmarksScreen

fun EntryProviderScope<NavKey>.bookmarksEntry(
    onPhotoClick: (PhotoDetail) -> Unit
) {
    entry<BookmarksNavKey> {
        BookmarksScreen(
            onPhotoClick = onPhotoClick
        )
    }
}
