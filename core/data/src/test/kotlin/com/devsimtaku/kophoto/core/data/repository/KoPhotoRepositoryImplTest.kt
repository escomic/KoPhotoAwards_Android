package com.devsimtaku.kophoto.core.data.repository

import androidx.paging.testing.asSnapshot
import com.devsimtaku.kophoto.core.data.db.dao.BookmarkDao
import com.devsimtaku.kophoto.core.data.db.entity.BookmarkEntity
import com.devsimtaku.kophoto.core.data.mapper.asDomain
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.network.PhotoDataSource
import com.devsimtaku.kophoto.core.network.model.KoPhotoHeader
import com.devsimtaku.kophoto.core.network.model.KoPhotoItems
import com.devsimtaku.kophoto.core.network.model.KoPhotoListBody
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponse
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponseBodyContainer
import com.devsimtaku.kophoto.core.network.model.PhotoAwardItem
import com.devsimtaku.kophoto.core.network.model.PhotoGalleryItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class KoPhotoRepositoryImplTest {

    private val photoDataSource: PhotoDataSource = mockk()
    private val bookmarkDao: BookmarkDao = mockk()
    private val repository = KoPhotoRepositoryImpl(photoDataSource, bookmarkDao)

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

    @Test
    fun `getBookmarks가 Flow List PhotoDetail 를 반환한다`() = runTest {
        val entities = listOf(
            BookmarkEntity("1", "url", "title", "loc", "day", "photog", "key", "desc")
        )
        every { bookmarkDao.getBookmarks() } returns flowOf(entities)

        val result = repository.getBookmarks().first()

        assertEquals(1, result.size)
        assertEquals("1", result[0].contentId)
    }

    @Test
    fun `addBookmark가 dao의 insert를 호출한다`() = runTest {
        val photo = PhotoDetail("1", "url", "title", "loc", "day", "photog", "key", "desc")
        coEvery { bookmarkDao.insertBookmark(any()) } returns 1L

        repository.addBookmark(photo)

        coVerify { bookmarkDao.insertBookmark(any()) }
    }

    @Test
    fun `removeBookmark가 dao의 delete를 호출한다`() = runTest {
        coEvery { bookmarkDao.deleteBookmark("1") } returns 1

        repository.removeBookmark("1")

        coVerify { bookmarkDao.deleteBookmark("1") }
    }

    @Test
    fun `isBookmarked가 dao의 exists여부를 반환한다`() = runTest {
        every { bookmarkDao.isBookmarked("1") } returns flowOf(true)

        val result = repository.isBookmarked("1").first()

        assertEquals(true, result)
    }
}
