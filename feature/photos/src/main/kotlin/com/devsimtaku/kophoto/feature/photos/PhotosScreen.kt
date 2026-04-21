package com.devsimtaku.kophoto.feature.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.devsimtaku.kophoto.core.designsystem.component.KPLoadingIndicator
import com.devsimtaku.kophoto.core.designsystem.component.KPPhotoItem
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiEffect
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel = hiltViewModel(),
    onPhotoClick: (PhotoDetail) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val photos = viewModel.photos.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is PhotosUiEffect.NavigateToDetail -> {
                    onPhotoClick(effect.photo.toArgs())
                }
            }
        }
    }

    val isRefreshing = photos.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = { photos.refresh() }
    ) {
        PhotosContent(
            photos = photos,
            onPhotoClick = { photo ->
                viewModel.sendEvent(PhotosUiEvent.OnPhotoClick(photo))
            }
        )
    }
}

@Composable
private fun PhotosContent(
    modifier: Modifier = Modifier,
    photos: LazyPagingItems<PhotoGallery>,
    onPhotoClick: (PhotoGallery) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            count = photos.itemCount,
            key = photos.itemKey { it.id }
        ) { index ->
            photos[index]?.let { photo ->
                KPPhotoItem(
                    imageUrl = photo.imageUrl,
                    title = photo.title,
                    photographer = photo.photographer,
                    onClick = { onPhotoClick(photo) }
                )
            }
        }

        when (val state = photos.loadState.append) {
            is LoadState.Loading -> {
                item {
                    KPLoadingIndicator()
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorItem(state.error.message ?: "Unknown Error")
                }
            }

            else -> {}
        }
    }
}

private fun PhotoGallery.toArgs(): PhotoDetail = PhotoDetail(
    contentId = id,
    imageUrl = imageUrl,
    title = title,
    location = location ?: "",
    filmDay = date ?: "",
    photographer = photographer ?: "",
    keyword = searchKeyword ?: "",
    description = ""
)

@Composable
private fun ErrorItem(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}
