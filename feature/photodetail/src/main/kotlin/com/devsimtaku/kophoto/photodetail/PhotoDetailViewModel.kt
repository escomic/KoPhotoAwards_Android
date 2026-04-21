package com.devsimtaku.kophoto.photodetail

import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.photodetail.contract.PhotoDetailUiEffect
import com.devsimtaku.kophoto.photodetail.contract.PhotoDetailUiEvent
import com.devsimtaku.kophoto.photodetail.contract.PhotoDetailUiState
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
        // Handle events
    }
}
