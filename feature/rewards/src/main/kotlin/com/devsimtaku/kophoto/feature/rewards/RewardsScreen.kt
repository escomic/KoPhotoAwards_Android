package com.devsimtaku.kophoto.feature.rewards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.devsimtaku.kophoto.core.designsystem.component.KPLoadingIndicator
import com.devsimtaku.kophoto.core.designsystem.component.KPPhotoItem
import com.devsimtaku.kophoto.core.domain.model.PhotoAward
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiEffect
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(
    modifier: Modifier = Modifier,
    viewModel: RewardsViewModel = hiltViewModel(),
    onPhotoClick: (PhotoDetail) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val rewards = viewModel.rewards.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is RewardsUiEffect.NavigateToDetail -> {
                    onPhotoClick(effect.reward.toArgs())
                }
            }
        }
    }

    val isRefreshing = rewards.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = { rewards.refresh() }
    ) {
        RewardsContent(
            rewards = rewards,
            onPhotoClick = { reward ->
                viewModel.sendEvent(RewardsUiEvent.OnPhotoClick(reward))
            }
        )
    }
}

@Composable
private fun RewardsContent(
    modifier: Modifier = Modifier,
    rewards: LazyPagingItems<PhotoAward>,
    onPhotoClick: (PhotoAward) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            count = rewards.itemCount,
            key = rewards.itemKey { it.id }
        ) { index ->
            rewards[index]?.let { reward ->
                KPPhotoItem(
                    imageUrl = reward.thumbnail ?: "",
                    title = reward.title,
                    photographer = reward.photographer,
                    onClick = { onPhotoClick(reward) }
                )
            }
        }

        when (val state = rewards.loadState.append) {
            is LoadState.Loading -> {
                item {
                    KPLoadingIndicator()
                }
            }
            else -> {}
        }
    }
}

private fun PhotoAward.toArgs(): PhotoDetail = PhotoDetail(
    contentId = id,
    imageUrl = original ?: thumbnail ?: "",
    title = title,
    location = location ?: "",
    filmDay = date ?: "",
    photographer = photographer ?: "",
    keyword = keyword ?: "",
    description = description ?: ""
)
