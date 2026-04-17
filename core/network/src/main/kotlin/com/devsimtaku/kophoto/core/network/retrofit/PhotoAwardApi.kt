package com.devsimtaku.kophoto.core.network.retrofit

import com.devsimtaku.kophoto.core.network.model.KoPhotoListBody
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponse
import com.devsimtaku.kophoto.core.network.model.PhotoAwardItem
import com.devsimtaku.kophoto.core.network.model.PhotoGalleryItem
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoAwardApi {
    @GET("B551011/PhokoAwrdService/phokoAwrdList")
    suspend fun getPhotoAwardList(
        @Query("numOfRows") numOfRows: Int? = 10,
        @Query("pageNo") pageNo: Int? = 1,
        @Query("MobileOS") mobileOS: String = "AND",
        @Query("MobileApp") mobileApp: String = "KoPhotos",
        @Query("_type") type: String = "json",
        @Query("arrange") arrange: String? = "C",
        @Query("mdfcnDt") mdfcnDt: String? = null,
        @Query("lDongRegnCd") lDongRegnCd: String? = null,
        @Query("keyword") keyword: String? = null
    ): KoPhotoResponse<KoPhotoListBody<PhotoAwardItem>>

    @GET("B551011/PhotoGalleryService1/galleryList1")
    suspend fun getGalleryList(
        @Query("numOfRows") numOfRows: Int? = 10,
        @Query("pageNo") pageNo: Int? = 1,
        @Query("MobileOS") mobileOS: String = "AND",
        @Query("MobileApp") mobileApp: String = "KoPhotos",
        @Query("_type") type: String = "json",
        @Query("arrange") arrange: String? = "C"
    ): KoPhotoResponse<KoPhotoListBody<PhotoGalleryItem>>
}
