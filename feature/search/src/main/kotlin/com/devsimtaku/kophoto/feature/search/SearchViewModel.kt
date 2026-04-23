package com.devsimtaku.kophoto.feature.search

import androidx.lifecycle.ViewModel
import com.devsimtaku.kophoto.feature.search.contract.SearchUiEvent
import com.devsimtaku.kophoto.feature.search.contract.SearchUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel(assistedFactory = SearchViewModel.Factory::class)
class SearchViewModel @AssistedInject constructor(
    @Assisted val initialQuery: String?
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState(searchQuery = initialQuery ?: ""))
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchQueryChanged -> {
                _uiState.value = _uiState.value.copy(searchQuery = event.query)
            }
            SearchUiEvent.OnSearchClick -> {
                // TODO: 검색 로직 구현
            }
            else -> { /* 처리 필요 없음 */ }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(initialQuery: String?): SearchViewModel
    }
}
