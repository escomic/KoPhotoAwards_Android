package com.devsimtaku.kophoto.feature.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devsimtaku.kophoto.core.designsystem.component.KPPhotoItem
import com.devsimtaku.kophoto.core.designsystem.component.KPPhotoSkeletonItem
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.feature.bookmarks.contract.BookmarksUiEffect
import com.devsimtaku.kophoto.feature.bookmarks.contract.BookmarksUiEvent

@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    viewModel: BookmarksViewModel = hiltViewModel(),
    onPhotoClick: (PhotoDetail) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is BookmarksUiEffect.NavigateToDetail -> {
                    onPhotoClick(effect.photo)
                }
            }
        }
    }

    BookmarksContent(
        modifier = modifier,
        uiState = uiState,
        onPhotoClick = { photo ->
            viewModel.sendEvent(BookmarksUiEvent.OnPhotoClick(photo))
        }
    )
}

@Composable
private fun BookmarksContent(
    modifier: Modifier = Modifier,
    uiState: com.devsimtaku.kophoto.feature.bookmarks.contract.BookmarksUiState,
    onPhotoClick: (PhotoDetail) -> Unit
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
                text = "즐겨찾기",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (uiState.isLoading) {
            items(5) {
                KPPhotoSkeletonItem()
            }
        } else if (uiState.bookmarks.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "즐겨찾기한 사진이 없습니다.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            items(
                items = uiState.bookmarks,
                key = { it.contentId }
            ) { photo ->
                KPPhotoItem(
                    imageUrl = photo.imageUrl,
                    title = photo.title,
                    photographer = photo.photographer,
                    onClick = { onPhotoClick(photo) }
                )
            }
        }
    }
}
