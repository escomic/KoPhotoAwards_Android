package com.devsimtaku.kophoto.core.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devsimtaku.kophoto.core.data.mapper.asDomain
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.network.PhotoDataSource
import com.devsimtaku.kophoto.core.network.model.getOrThrow

class PhotoGalleryPagingSource(
    private val photoDataSource: PhotoDataSource,
    private val arrange: String?,
    private val query: String?
) : PagingSource<Int, PhotoGallery>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoGallery> {
        val page = params.key ?: 1
        return try {
            val responseBody = if (query.isNullOrEmpty()) {
                photoDataSource.getGalleryList(
                    numOfRows = params.loadSize,
                    pageNo = page,
                    arrange = arrange
                ).getOrThrow()
            } else {
                photoDataSource.searchGalleryList(
                    numOfRows = params.loadSize,
                    pageNo = page,
                    arrange = arrange,
                    keyword = query
                ).getOrThrow()
            }

            val items = responseBody.items?.item?.map { it.asDomain() } ?: emptyList()

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.size < params.loadSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoGallery>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
