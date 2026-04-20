package com.devsimtaku.kophoto.core.data.repository

import androidx.paging.testing.asSnapshot
import com.devsimtaku.kophoto.core.data.mapper.asDomain
import com.devsimtaku.kophoto.core.network.PhotoDataSource
import com.devsimtaku.kophoto.core.network.model.KoPhotoHeader
import com.devsimtaku.kophoto.core.network.model.KoPhotoItems
import com.devsimtaku.kophoto.core.network.model.KoPhotoListBody
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponse
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponseBodyContainer
import com.devsimtaku.kophoto.core.network.model.PhotoAwardItem
import com.devsimtaku.kophoto.core.network.model.PhotoGalleryItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class KoPhotoRepositoryImplTest {

    private val photoDataSource: PhotoDataSource = mockk()
    private val repository = KoPhotoRepositoryImpl(photoDataSource)

    @Test
    fun `getPhotoAwardList가 페이징 데이터 Flow를 반환한다`() = runTest {
        val items = listOf(
            PhotoAwardItem(contentId = "1", koTitle = "Title 1"),
            PhotoAwardItem(contentId = "2", koTitle = "Title 2")
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
            photoDataSource.getPhotoAwardList(any(), any(), any(), any(), any(), any())
        } returns response

        val result = repository.getPhotoAwardList().asSnapshot()

        assertEquals(items.map { it.asDomain() }, result)
    }

    @Test
    fun `getGalleryList가 페이징 데이터 Flow를 반환한다`() = runTest {
        val items = listOf(
            PhotoGalleryItem(
                galContentId = "1",
                galTitle = "Title 1",
                galContentTypeId = "1",
                galWebImageUrl = "url1",
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
                    totalCount = 1
                )
            )
        )

        coEvery {
            photoDataSource.getGalleryList(any(), any(), any())
        } returns response

        val result = repository.getGalleryList().asSnapshot()

        assertEquals(items.map { it.asDomain() }, result)
    }
}
