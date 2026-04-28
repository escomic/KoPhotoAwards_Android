package com.devsimtaku.kophoto.feature.rewards

import androidx.compose.foundation.layout.Arrangement
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
import com.devsimtaku.kophoto.core.domain.model.PhotoAward
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.ui.util.toErrorMessage
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiEffect
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiEvent
import com.devsimtaku.kophoto.core.ui.R as CoreUiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(
    modifier: Modifier = Modifier,
    viewModel: RewardsViewModel = hiltViewModel(),
    onPhotoClick: (PhotoDetail) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val rewards = viewModel.rewards.collectAsLazyPagingItems()
    var errorToShow by remember { mutableStateOf<Throwable?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is RewardsUiEffect.NavigateToDetail -> {
                    onPhotoClick(effect.reward.toArgs())
                }

                is RewardsUiEffect.ShowErrorDialog -> {
                    errorToShow = effect.throwable
                }
            }
        }
    }

    LaunchedEffect(rewards.loadState.refresh) {
        if (rewards.loadState.refresh is LoadState.Error) {
            errorToShow = (rewards.loadState.refresh as LoadState.Error).error
        }
    }

    if (errorToShow != null) {
        KPErrorDialog(
            title = stringResource(id = CoreUiR.string.core_error),
            message = errorToShow!!.toErrorMessage(LocalContext.current),
            onDismissRequest = { errorToShow = null }
        )
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
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            Text(
                text = "수상작",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }

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

        when (rewards.loadState.append) {
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
