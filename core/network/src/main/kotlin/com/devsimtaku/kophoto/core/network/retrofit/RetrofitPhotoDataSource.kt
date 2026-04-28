package com.devsimtaku.kophoto.core.network.retrofit

import com.devsimtaku.kophoto.core.network.PhotoDataSource
import com.devsimtaku.kophoto.core.network.model.KoPhotoListBody
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponse
import com.devsimtaku.kophoto.core.network.model.PhotoAwardItem
import com.devsimtaku.kophoto.core.network.model.PhotoGalleryItem

internal class RetrofitPhotoDataSource(
    private val koPhotoApi: KoPhotoApi
) : PhotoDataSource {
    override suspend fun getPhotoAwardList(
        numOfRows: Int?,
        pageNo: Int?,
        arrange: String?,
        mdfcnDt: String?,
        lDongRegnCd: String?,
        keyword: String?
    ): KoPhotoResponse<KoPhotoListBody<PhotoAwardItem>> {
        return koPhotoApi.getPhotoAwardList(
            numOfRows = numOfRows,
            pageNo = pageNo,
            arrange = arrange,
            mdfcnDt = mdfcnDt,
            lDongRegnCd = lDongRegnCd,
            keyword = keyword
        )
    }

    override suspend fun getGalleryList(
        numOfRows: Int?,
        pageNo: Int?,
        arrange: String?
    ): KoPhotoResponse<KoPhotoListBody<PhotoGalleryItem>> {
        return koPhotoApi.getGalleryList(
            numOfRows = numOfRows,
            pageNo = pageNo,
            arrange = arrange
        )
    }

    override suspend fun searchGalleryList(
        numOfRows: Int?,
        pageNo: Int?,
        arrange: String?,
        keyword: String
    ): KoPhotoResponse<KoPhotoListBody<PhotoGalleryItem>> {
        return koPhotoApi.searchGalleryList(
            numOfRows = numOfRows,
            pageNo = pageNo,
            arrange = arrange,
            keyword = keyword
        )
    }
}
