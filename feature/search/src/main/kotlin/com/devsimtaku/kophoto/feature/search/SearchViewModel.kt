package com.devsimtaku.kophoto.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.search.contract.SearchUiEffect
import com.devsimtaku.kophoto.feature.search.contract.SearchUiEvent
import com.devsimtaku.kophoto.feature.search.contract.SearchUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

@HiltViewModel(assistedFactory = SearchViewModel.Factory::class)
class SearchViewModel @AssistedInject constructor(
    @Assisted private val initialQuery: String?,
    private val repository: KoPhotoRepository
) : BaseViewModel<SearchUiState, SearchUiEvent, SearchUiEffect>(
    SearchUiState(searchQuery = initialQuery ?: "")
) {

    private val _searchKey = MutableStateFlow(initialQuery ?: "")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val photos: Flow<PagingData<PhotoGallery>> = _searchKey
        .debounce(500L)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repository.searchGalleryList(query = query)
            }
        }
        .cachedIn(viewModelScope)

    override fun handleEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.UpdateSearchQuery -> {
                setState { copy(searchQuery = event.query) }
                _searchKey.value = event.query
            }

            SearchUiEvent.SearchPhotos -> {
                _searchKey.value = uiState.value.searchQuery
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(initialQuery: String?): SearchViewModel
    }
}
