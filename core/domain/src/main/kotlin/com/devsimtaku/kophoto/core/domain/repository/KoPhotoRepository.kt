package com.devsimtaku.kophoto.core.domain.repository

import androidx.paging.PagingData
import com.devsimtaku.kophoto.core.domain.model.PhotoAward
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import kotlinx.coroutines.flow.Flow

interface KoPhotoRepository {

    fun getPhotoAwardList(
        arrange: String? = "C",
        mdfcnDt: String? = null,
        lDongRegnCd: String? = null,
        keyword: String? = null
    ): Flow<PagingData<PhotoAward>>

    fun getGalleryList(
        arrange: String? = "C"
    ): Flow<PagingData<PhotoGallery>>

    fun searchGalleryList(
        query: String,
        arrange: String? = "C"
    ): Flow<PagingData<PhotoGallery>>
}
