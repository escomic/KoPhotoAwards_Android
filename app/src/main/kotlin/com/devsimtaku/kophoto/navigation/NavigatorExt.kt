package com.devsimtaku.kophoto.navigation

import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.navigation.Navigator
import com.devsimtaku.kophoto.feature.imageviewer.navigation.ImageViewerNavKey
import com.devsimtaku.kophoto.feature.photodetail.navigation.PhotoDetailNavKey
import com.devsimtaku.kophoto.feature.search.navigation.SearchNavKey

/**
 * Extension to navigate to the PhotoDetail screen.
 */
fun Navigator.navigateToPhotoDetail(
    item: PhotoDetail
) {
    navigate(
        key = PhotoDetailNavKey(item = item)
    )
}

/**
 * Extension to navigate to the Search screen.
 */
fun Navigator.navigateToSearch(
    query: String? = null
) {
    navigate(
        key = SearchNavKey(query = query)
    )
}

/**
 * Extension to navigate to the ImageViewer screen.
 */
fun Navigator.navigateToImageViewer(
    imageUrl: String,
    title: String? = null
) {
    navigate(
        key = ImageViewerNavKey(imageUrl = imageUrl, title = title)
    )
}
