package com.devsimtaku.kophoto.core.network

import com.devsimtaku.kophoto.core.network.model.KoPhotoListBody
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponse
import com.devsimtaku.kophoto.core.network.model.PhotoAwardItem
import com.devsimtaku.kophoto.core.network.model.PhotoGalleryItem

interface PhotoDataSource {
    suspend fun getPhotoAwardList(
        numOfRows: Int? = 10,
        pageNo: Int? = 1,
        arrange: String? = "C",
        mdfcnDt: String? = null,
        lDongRegnCd: String? = null,
        keyword: String? = null
    ): KoPhotoResponse<KoPhotoListBody<PhotoAwardItem>>

    suspend fun getGalleryList(
        numOfRows: Int? = 10,
        pageNo: Int? = 1,
        arrange: String? = "C"
    ): KoPhotoResponse<KoPhotoListBody<PhotoGalleryItem>>
}
