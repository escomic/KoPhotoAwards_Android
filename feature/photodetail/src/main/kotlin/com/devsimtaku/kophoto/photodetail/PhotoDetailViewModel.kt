package com.devsimtaku.kophoto.photodetail

import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.photodetail.contract.PhotoDetailUiEffect
import com.devsimtaku.kophoto.photodetail.contract.PhotoDetailUiEvent
import com.devsimtaku.kophoto.photodetail.contract.PhotoDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor() : BaseViewModel<PhotoDetailUiState, PhotoDetailUiEvent, PhotoDetailUiEffect>(
    PhotoDetailUiState(id = "")
) {
    override fun handleEvent(event: PhotoDetailUiEvent) {
    }
}

