package com.devsimtaku.kophoto.core.data.mapper

import com.devsimtaku.kophoto.core.domain.model.PhotoAward
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.network.model.PhotoAwardItem
import com.devsimtaku.kophoto.core.network.model.PhotoGalleryItem

fun PhotoAwardItem.asDomain(): PhotoAward {
    return PhotoAward(
        id = contentId,
        title = koTitle,
        photographer = koCmanNm,
        thumbnail = thumbImage,
        original = orgImage,
        location = koFilmst,
        description = koWnprzDiz,
        keyword = koKeyWord,
        date = filmDay
    )
}

fun PhotoGalleryItem.asDomain(): PhotoGallery {
    return PhotoGallery(
        id = galContentId,
        title = galTitle,
        imageUrl = galWebImageUrl,
        photographer = galPhotographer,
        location = galPhotographyLocation,
        date = galPhotographyMonth,
        searchKeyword = galSearchKeyword
    )
}
