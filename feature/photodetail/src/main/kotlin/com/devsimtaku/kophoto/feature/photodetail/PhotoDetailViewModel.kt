package com.devsimtaku.kophoto.feature.photodetail

import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiEffect
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiEvent
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = PhotoDetailViewModel.Factory::class)
class PhotoDetailViewModel @AssistedInject constructor(
    @Assisted private val item: PhotoDetail
) : BaseViewModel<PhotoDetailUiState, PhotoDetailUiEvent, PhotoDetailUiEffect>(
    PhotoDetailUiState(item = item)
) {
    @AssistedFactory
    interface Factory {
        fun create(item: PhotoDetail): PhotoDetailViewModel
    }

    override fun handleEvent(event: PhotoDetailUiEvent) {
        when (event) {
            is PhotoDetailUiEvent.OnKeywordClick -> {
                sendEffect(PhotoDetailUiEffect.NavigateToSearch(event.keyword))
            }
        }
    }
}
