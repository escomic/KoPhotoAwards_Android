package com.devsimtaku.kophoto.navigation

import com.devsimtaku.kophoto.core.navigation.Navigator
import com.devsimtaku.kophoto.photodetail.navigation.PhotoDetailNavKey

fun Navigator.navigateToPhotoDetail(id: String) {
    navigate(PhotoDetailNavKey(id))
}