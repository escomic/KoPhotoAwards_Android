package com.devsimtaku.kophoto.feature.search.contract

sealed interface SearchUiEvent {
    data class OnSearchQueryChanged(val query: String) : SearchUiEvent
    data object OnSearchClick : SearchUiEvent
    data class OnPhotoClick(val item: com.devsimtaku.kophoto.core.domain.model.PhotoDetail) : SearchUiEvent
    data object OnBackClick : SearchUiEvent
}
