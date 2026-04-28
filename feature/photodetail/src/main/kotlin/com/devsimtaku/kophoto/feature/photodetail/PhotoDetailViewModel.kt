package com.devsimtaku.kophoto.feature.photodetail

import androidx.lifecycle.viewModelScope
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiEffect
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiEvent
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PhotoDetailViewModel.Factory::class)
class PhotoDetailViewModel @AssistedInject constructor(
    @Assisted private val item: PhotoDetail,
    private val repository: KoPhotoRepository
) : BaseViewModel<PhotoDetailUiState, PhotoDetailUiEvent, PhotoDetailUiEffect>(
    PhotoDetailUiState(item = item)
) {
    @AssistedFactory
    interface Factory {
        fun create(item: PhotoDetail): PhotoDetailViewModel
    }

    init {
        observeBookmarkStatus()
    }

    private fun observeBookmarkStatus() {
        repository.isBookmarked(item.contentId)
            .onEach { isBookmarked ->
                setState { copy(isBookmarked = isBookmarked) }
            }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: PhotoDetailUiEvent) {
        when (event) {
            is PhotoDetailUiEvent.OnKeywordClick -> {
                sendEffect(PhotoDetailUiEffect.NavigateToSearch(event.keyword))
            }
            is PhotoDetailUiEvent.OnImageClick -> {
                sendEffect(PhotoDetailUiEffect.NavigateToImageViewer(event.imageUrl, event.title))
            }
            is PhotoDetailUiEvent.OnBookmarkClick -> {
                toggleBookmark()
            }
        }
    }

    private fun toggleBookmark() {
        viewModelScope.launch {
            if (uiState.value.isBookmarked) {
                repository.removeBookmark(item.contentId)
            } else {
                repository.addBookmark(item)
            }
        }
    }
}
