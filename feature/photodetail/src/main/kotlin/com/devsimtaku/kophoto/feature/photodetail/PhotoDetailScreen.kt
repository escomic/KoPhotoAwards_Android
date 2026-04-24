package com.devsimtaku.kophoto.feature.photodetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devsimtaku.kophoto.core.designsystem.component.KPAsyncImage
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiEffect
import com.devsimtaku.kophoto.feature.photodetail.contract.PhotoDetailUiEvent

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
    onNavigateToSearch: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val args = uiState.item

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is PhotoDetailUiEffect.NavigateToSearch -> {
                    onNavigateToSearch(effect.keyword)
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = args?.title ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
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
            ) {
                KPAsyncImage(
                    model = args.imageUrl,
                    contentDescription = args.title,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    DetailRow(label = "촬영자", value = args.photographer)
                    DetailRow(
                        label = "촬영일",
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
                    DetailRow(label = "촬영장소", value = args.location)
                    KeywordRow(
                        label = "키워드",
                        keywords = args.keyword,
                        onKeywordClick = { keyword ->
                            viewModel.sendEvent(PhotoDetailUiEvent.OnKeywordClick(keyword))
                        }
                    )

                    if (args.description.isNotBlank()) {
                        DetailRow(label = "부가정보", value = args.description)
                    }
                }
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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(80.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        
        val keywordList = keywords.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        
        FlowRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            keywordList.forEach { keyword ->
                AssistChip(
                    onClick = { onKeywordClick(keyword) },
                    label = { 
                        Text(
                            text = "#$keyword",
                            style = MaterialTheme.typography.bodySmall
                        ) 
                    },
                    border = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderColor = MaterialTheme.colorScheme.outlineVariant
                    )
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(80.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
