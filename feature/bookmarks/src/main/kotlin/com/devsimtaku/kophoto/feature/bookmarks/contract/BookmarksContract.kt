package com.devsimtaku.kophoto.feature.bookmarks.contract

import com.devsimtaku.kophoto.core.domain.model.PhotoDetail

data class BookmarksUiState(
    val bookmarks: List<PhotoDetail> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface BookmarksUiEvent {
    data class OnPhotoClick(val photo: PhotoDetail) : BookmarksUiEvent
}

sealed interface BookmarksUiEffect {
    data class NavigateToDetail(val photo: PhotoDetail) : BookmarksUiEffect
}
