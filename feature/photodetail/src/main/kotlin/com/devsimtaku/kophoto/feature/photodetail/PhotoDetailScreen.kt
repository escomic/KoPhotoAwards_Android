package com.devsimtaku.kophoto.feature.photodetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devsimtaku.kophoto.core.designsystem.KPIcon
import com.devsimtaku.kophoto.core.designsystem.component.KPAsyncImage
import com.devsimtaku.kophoto.core.designsystem.component.KPChip
import com.devsimtaku.kophoto.core.designsystem.component.KPChipStyle
import com.devsimtaku.kophoto.core.designsystem.component.KPErrorDialog
import com.devsimtaku.kophoto.core.designsystem.theme.KoPhotoTheme
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.ui.util.toErrorMessage
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiEffect
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiEvent
import com.devsimtaku.kophoto.core.ui.R as CoreUiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    modifier: Modifier = Modifier,
    item: PhotoDetail,
    viewModel: PhotoDetailViewModel = hiltViewModel(
        creationCallback = { factory: PhotoDetailViewModel.Factory ->
            factory.create(item)
        },
        key = item.contentId
    ),
    onBackClick: () -> Unit,
    onNavigateToSearch: (String) -> Unit,
    onImageClick: (String, String?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val args = uiState.item
    var errorToShow by remember { mutableStateOf<Throwable?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is PhotoDetailUiEffect.NavigateToSearch -> {
                    onNavigateToSearch(effect.keyword)
                }

                is PhotoDetailUiEffect.ShowErrorDialog -> {
                    errorToShow = effect.throwable
                }

                is PhotoDetailUiEffect.NavigateToImageViewer -> {
                    onImageClick(effect.imageUrl, effect.title)
                }
            }
        }
    }

    if (errorToShow != null) {
        KPErrorDialog(
            title = stringResource(id = CoreUiR.string.core_error),
            message = errorToShow!!.toErrorMessage(LocalContext.current),
            onDismissRequest = { errorToShow = null }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = KPIcon.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.sendEvent(PhotoDetailUiEvent.OnBookmarkClick) }) {
                        Icon(
                            imageVector = if (uiState.isBookmarked) KPIcon.StarFilled else KPIcon.Star,
                            contentDescription = "Bookmark",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (args != null) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 56.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                KPAsyncImage(
                    model = args.imageUrl,
                    contentDescription = args.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .clickable {
                            viewModel.sendEvent(PhotoDetailUiEvent.OnImageClick(args.imageUrl, args.title))
                        },
                    contentScale = ContentScale.FillWidth
                )

                Text(
                    text = args.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                DetailRow(
                    icon = { KPIcon.PhotoCamera },
                    title = "촬영자",
                    value = args.photographer
                )
                DetailRow(
                    icon = { KPIcon.Today },
                    title = "촬영일",
                    value = if (args.filmDay.length == 6) {
                        stringResource(
                            R.string.photo_detail_film_day_format,
                            args.filmDay.substring(0, 4),
                            args.filmDay.substring(4, 6)
                        )
                    } else {
                        args.filmDay
                    }
                )
                DetailRow(
                    icon = { KPIcon.LocationOn },
                    title = "촬영장소",
                    value = args.location
                )
                if (args.description.isNotBlank()) {
                    DetailRow(
                        icon = { KPIcon.Article },
                        title = "부가정보",
                        value = args.description
                    )
                }
                KeywordRow(
                    label = "키워드",
                    keywords = args.keyword,
                    onKeywordClick = { keyword ->
                        viewModel.sendEvent(PhotoDetailUiEvent.OnKeywordClick(keyword))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun KeywordRow(
    label: String,
    keywords: String,
    onKeywordClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )
        val keywordList = keywords.split(",").map { it.trim() }.filter { it.isNotEmpty() }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            keywordList.forEach { keyword ->
                KPChip(
                    text = "#$keyword",
                    style = KPChipStyle.Normal,
                    onClick = { onKeywordClick(keyword) }
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: @Composable () -> ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailRowPreview() {
    KoPhotoTheme {
        DetailRow(
            icon = { KPIcon.PhotoCamera },
            title = "촬영자",
            value = "이범수",
            modifier = Modifier.padding(16.dp)
        )
    }
}
