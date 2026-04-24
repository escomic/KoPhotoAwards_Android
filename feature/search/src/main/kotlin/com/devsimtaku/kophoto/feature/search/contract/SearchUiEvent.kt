package com.devsimtaku.kophoto.feature.search.contract

sealed interface SearchUiEvent {
    data class UpdateSearchQuery(val query: String) : SearchUiEvent
    data object SearchPhotos : SearchUiEvent
}
