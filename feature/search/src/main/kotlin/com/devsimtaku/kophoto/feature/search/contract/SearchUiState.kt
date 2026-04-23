package com.devsimtaku.kophoto.feature.search.contract

import com.devsimtaku.kophoto.core.domain.model.PhotoDetail

data class SearchUiState(
    val searchQuery: String = "",
    val searchResult: List<PhotoDetail> = emptyList(),
    val isLoading: Boolean = false
)
