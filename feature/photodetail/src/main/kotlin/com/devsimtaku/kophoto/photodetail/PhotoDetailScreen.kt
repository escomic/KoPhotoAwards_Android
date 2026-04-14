package com.devsimtaku.kophoto.photodetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun PhotoDetailScreen(
    photoId: String,
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {


}