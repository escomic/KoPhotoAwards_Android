package com.devsimtaku.kophoto.feature.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
import com.devsimtaku.kophoto.core.designsystem.component.KPTextField
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.ui.util.toErrorMessage
import com.devsimtaku.kophoto.feature.search.contract.SearchUiEffect
import com.devsimtaku.kophoto.feature.search.contract.SearchUiEvent
import com.devsimtaku.kophoto.core.ui.R as CoreUiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    initialQuery: String?,
    onBackClick: () -> Unit,
    onPhotoClick: (PhotoDetail) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(
        creationCallback = { factory: SearchViewModel.Factory ->
            factory.create(initialQuery)
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val photos = viewModel.photos.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current
    var errorToShow by remember { mutableStateOf<Throwable?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SearchUiEffect.ShowToast -> {
                    // Toast handling if needed
                }
                is SearchUiEffect.ShowErrorDialog -> {
                    errorToShow = effect.throwable
                }
            }
        }
    }

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.feature_search_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(CoreUiR.string.core_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        SearchContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            searchQuery = uiState.searchQuery,
            photos = photos,
            onQueryChange = { viewModel.sendEvent(SearchUiEvent.UpdateSearchQuery(it)) },
            onSearch = {
                viewModel.sendEvent(SearchUiEvent.SearchPhotos)
                focusManager.clearFocus()
            },
            onClearQuery = { viewModel.sendEvent(SearchUiEvent.UpdateSearchQuery("")) },
            onPhotoClick = { photo ->
                onPhotoClick(photo.toDetail())
            }
        )
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    KPTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        hint = stringResource(R.string.feature_search_placeholder),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onClearQuery
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() })
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    photos: LazyPagingItems<PhotoGallery>,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClearQuery: () -> Unit,
    onPhotoClick: (PhotoGallery) -> Unit
) {
    val isRefreshLoading = photos.loadState.refresh is LoadState.Loading
    val isError = photos.loadState.refresh is LoadState.Error
    val isEmpty = photos.loadState.refresh is LoadState.NotLoading && photos.itemCount == 0

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 12.dp, bottom = 4.dp)
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch,
                    onClearQuery = onClearQuery
                )
            }
        }

        if (searchQuery.isBlank()) {
            return@LazyColumn
        }

        when {
            isRefreshLoading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        KPLoadingIndicator()
                    }
                }
            }

            isError -> {
                val error = (photos.loadState.refresh as LoadState.Error).error
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = error.message ?: "Unknown Error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            isEmpty -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.feature_search_empty),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            else -> {
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

                if (photos.loadState.append is LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            KPLoadingIndicator()
                        }
                    }
                }
            }
        }
    }
}

private fun PhotoGallery.toDetail(): PhotoDetail = PhotoDetail(
    contentId = id,
    imageUrl = imageUrl,
    title = title,
    location = location ?: "",
    filmDay = date ?: "",
    photographer = photographer ?: "",
    keyword = searchKeyword ?: "",
    description = ""
)
