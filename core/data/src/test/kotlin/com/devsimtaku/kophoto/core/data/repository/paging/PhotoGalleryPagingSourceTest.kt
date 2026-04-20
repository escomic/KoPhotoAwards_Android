package com.devsimtaku.kophoto.core.data.repository.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.devsimtaku.kophoto.core.data.mapper.asDomain
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.network.PhotoDataSource
import com.devsimtaku.kophoto.core.network.model.KoPhotoHeader
import com.devsimtaku.kophoto.core.network.model.KoPhotoItems
import com.devsimtaku.kophoto.core.network.model.KoPhotoListBody
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponse
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponseBodyContainer
import com.devsimtaku.kophoto.core.network.model.PhotoGalleryItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PhotoGalleryPagingSourceTest {

    private val photoDataSource: PhotoDataSource = mockk()

    @Test
    fun `유효한 데이터가 있을 때 로드 성공`() = runTest {
        val items = listOf(
            PhotoGalleryItem(
                galContentId = "1",
                galTitle = "Title 1",
                galContentTypeId = "1",
                galWebImageUrl = "url1",
                galCreatedtime = "2023",
                galModifiedtime = "2023"
            ),
            PhotoGalleryItem(
                galContentId = "2",
                galTitle = "Title 2",
                galContentTypeId = "1",
                galWebImageUrl = "url2",
                galCreatedtime = "2023",
                galModifiedtime = "2023"
            )
        )
        val response = KoPhotoResponse(
            response = KoPhotoResponseBodyContainer(
                header = KoPhotoHeader("0000", "OK"),
                body = KoPhotoListBody(
                    items = KoPhotoItems(items),
                    numOfRows = 10,
                    pageNo = 1,
                    totalCount = 2
                )
            )
        )

        coEvery {
            photoDataSource.getGalleryList(any(), any(), any())
        } returns response

        val pagingSource = PhotoGalleryPagingSource(
            photoDataSource = photoDataSource,
            arrange = "C"
        )

        val pager = TestPager(PagingConfig(pageSize = 10), pagingSource)
        val result = pager.refresh() as PagingSource.LoadResult.Page<Int, PhotoGallery>

        assertEquals(items.map { it.asDomain() }, result.data)
        assertEquals(null, result.prevKey)
        assertEquals(null, result.nextKey)
    }

    @Test
    fun `데이터 소스에서 예외 발생 시 에러 반환`() = runTest {
        val exception = RuntimeException("Network Error")
        coEvery {
            photoDataSource.getGalleryList(any(), any(), any())
        } throws exception

        val pagingSource = PhotoGalleryPagingSource(
            photoDataSource = photoDataSource,
            arrange = "C"
        )

        val pager = TestPager(PagingConfig(pageSize = 10), pagingSource)
        val result = pager.refresh()

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(exception, (result as PagingSource.LoadResult.Error).throwable)
    }
}
