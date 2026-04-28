package com.devsimtaku.kophoto.feature.bookmarks

import androidx.lifecycle.viewModelScope
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.bookmarks.contract.BookmarksUiEffect
import com.devsimtaku.kophoto.feature.bookmarks.contract.BookmarksUiEvent
import com.devsimtaku.kophoto.feature.bookmarks.contract.BookmarksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val koPhotoRepository: KoPhotoRepository
) : BaseViewModel<BookmarksUiState, BookmarksUiEvent, BookmarksUiEffect>(
    BookmarksUiState()
) {
    init {
        observeBookmarks()
    }

    private fun observeBookmarks() {
        koPhotoRepository.getBookmarks()
            .onStart { setState { copy(isLoading = true) } }
            .onEach { bookmarks ->
                setState { copy(bookmarks = bookmarks, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: BookmarksUiEvent) {
        when (event) {
            is BookmarksUiEvent.OnPhotoClick -> {
                sendEffect(BookmarksUiEffect.NavigateToDetail(event.photo))
            }
        }
    }
}
