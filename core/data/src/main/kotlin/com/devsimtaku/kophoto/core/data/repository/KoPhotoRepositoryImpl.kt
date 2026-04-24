package com.devsimtaku.kophoto.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.devsimtaku.kophoto.core.data.repository.paging.PhotoAwardPagingSource
import com.devsimtaku.kophoto.core.data.repository.paging.PhotoGalleryPagingSource
import com.devsimtaku.kophoto.core.domain.model.PhotoAward
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import com.devsimtaku.kophoto.core.network.PhotoDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KoPhotoRepositoryImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource
) : KoPhotoRepository {

    override fun getPhotoAwardList(
        arrange: String?,
        mdfcnDt: String?,
        lDongRegnCd: String?,
        keyword: String?
    ): Flow<PagingData<PhotoAward>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PhotoAwardPagingSource(
                    photoDataSource = photoDataSource,
                    arrange = arrange,
                    mdfcnDt = mdfcnDt,
                    lDongRegnCd = lDongRegnCd,
                    keyword = keyword
                )
            }
        ).flow
    }

    override fun getGalleryList(
        arrange: String?
    ): Flow<PagingData<PhotoGallery>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PhotoGalleryPagingSource(
                    photoDataSource = photoDataSource,
                    arrange = arrange,
                    query = null
                )
            }
        ).flow
    }

    override fun searchGalleryList(
        query: String,
        arrange: String?
    ): Flow<PagingData<PhotoGallery>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PhotoGalleryPagingSource(
                    photoDataSource = photoDataSource,
                    arrange = arrange,
                    query = query
                )
            }
        ).flow
    }
}
