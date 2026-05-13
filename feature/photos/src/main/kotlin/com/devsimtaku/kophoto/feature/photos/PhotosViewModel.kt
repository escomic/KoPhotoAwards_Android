package com.devsimtaku.kophoto.feature.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiEffect
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiEvent
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val koPhotoRepository: KoPhotoRepository
) : BaseViewModel<PhotosUiState, PhotosUiEvent, PhotosUiEffect>(
    PhotosUiState()
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    val photos: Flow<PagingData<PhotoGallery>> = uiState
        .map { it.selectedSortOption }
        .distinctUntilChanged()
        .flatMapLatest { sortOption ->
            koPhotoRepository.getGalleryList(arrange = sortOption.code)
        }
        .cachedIn(viewModelScope)

    override fun handleEvent(event: PhotosUiEvent) {
        when (event) {
            is PhotosUiEvent.OnPhotoClick -> {
                sendEffect(PhotosUiEffect.NavigateToDetail(event.photo))
            }
            is PhotosUiEvent.OnSortOptionSelected -> {
                if (uiState.value.selectedSortOption != event.option) {
                    setState { copy(selectedSortOption = event.option) }
                }
            }
        }
    }
}
