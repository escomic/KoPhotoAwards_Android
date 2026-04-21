package com.devsimtaku.kophoto.feature.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiEffect
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiEvent
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val koPhotoRepository: KoPhotoRepository
) : BaseViewModel<PhotosUiState, PhotosUiEvent, PhotosUiEffect>(
    PhotosUiState()
) {
    val photos = koPhotoRepository.getGalleryList()
        .cachedIn(viewModelScope)

    override fun handleEvent(event: PhotosUiEvent) {
        when (event) {
            is PhotosUiEvent.OnPhotoClick -> {
                sendEffect(PhotosUiEffect.NavigateToDetail(event.photo))
            }
        }
    }
}
