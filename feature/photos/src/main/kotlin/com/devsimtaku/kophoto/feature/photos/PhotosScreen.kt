package com.devsimtaku.kophoto.feature.photos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.devsimtaku.kophoto.core.designsystem.component.KPErrorDialog
import com.devsimtaku.kophoto.core.designsystem.component.KPLoadingIndicator
import com.devsimtaku.kophoto.core.designsystem.component.KPPhotoItem
import com.devsimtaku.kophoto.core.designsystem.component.KPPhotoSkeletonItem
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.ui.util.toErrorMessage
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiEffect
import com.devsimtaku.kophoto.feature.photos.contract.PhotosUiEvent
import com.devsimtaku.kophoto.core.ui.R as CoreUiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel = hiltViewModel(),
    onPhotoClick: (PhotoDetail) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val photos = viewModel.photos.collectAsLazyPagingItems()
    var errorToShow by remember { mutableStateOf<Throwable?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is PhotosUiEffect.NavigateToDetail -> {
                    onPhotoClick(effect.photo.toArgs())
                }
                is PhotosUiEffect.ShowErrorDialog -> {
                    errorToShow = effect.throwable
                }
            }
        }
    }

    // Paging 에러 감지 (Refresh)
    LaunchedEffect(photos.loadState.refresh) {
        if (photos.loadState.refresh is LoadState.Error) {
            errorToShow = (photos.loadState.refresh as LoadState.Error).error
        }
    }

    if (errorToShow != null) {
        KPErrorDialog(
            title = stringResource(id = CoreUiR.string.core_error),
            message = errorToShow!!.toErrorMessage(LocalContext.current),
            onDismissRequest = { errorToShow = null }
        )
    }

    // 첫 진입 로딩 여부 (데이터가 하나도 없을 때 로딩 중인 경우)
    val isInitialLoading = photos.loadState.refresh is LoadState.Loading && photos.itemCount == 0
    // 새로고침 인디케이터 표시 여부 (이미 데이터가 있을 때 로딩 중인 경우)
    val isRefreshing = photos.loadState.refresh is LoadState.Loading && photos.itemCount > 0

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = { photos.refresh() }
    ) {
        PhotosContent(
            photos = photos,
            isInitialLoading = isInitialLoading,
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
    isInitialLoading: Boolean,
    onPhotoClick: (PhotoGallery) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            Text(
                text = "한국관광갤러리",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (isInitialLoading) {
            items(5) {
                KPPhotoSkeletonItem()
            }
        } else {
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

            when (photos.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        KPLoadingIndicator()
                    }
                }
                else -> {}
            }
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
