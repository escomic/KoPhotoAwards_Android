package com.devsimtaku.kophoto.feature.imageviewer

import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.imageviewer.contract.ImageViewerUiEffect
import com.devsimtaku.kophoto.feature.imageviewer.contract.ImageViewerUiEvent
import com.devsimtaku.kophoto.feature.imageviewer.contract.ImageViewerUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ImageViewerViewModel.Factory::class)
class ImageViewerViewModel @AssistedInject constructor(
    @Assisted("imageUrl") private val imageUrl: String,
    @Assisted("title") private val title: String?
) : BaseViewModel<ImageViewerUiState, ImageViewerUiEvent, ImageViewerUiEffect>(
    ImageViewerUiState(imageUrl = imageUrl, title = title)
) {
    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("imageUrl") imageUrl: String,
            @Assisted("title") title: String?
        ): ImageViewerViewModel
    }

    override fun handleEvent(event: ImageViewerUiEvent) {
        when (event) {
            ImageViewerUiEvent.OnBackClick -> {
                sendEffect(ImageViewerUiEffect.NavigateBack)
            }
        }
    }
}
