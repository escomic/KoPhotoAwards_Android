package com.devsimtaku.kophoto.core.network.retrofit

import com.devsimtaku.kophoto.core.network.model.KoPhotoListBody
import com.devsimtaku.kophoto.core.network.model.KoPhotoResponse
import com.devsimtaku.kophoto.core.network.model.PhotoAwardItem
import com.devsimtaku.kophoto.core.network.model.PhotoGalleryItem
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class PhotoAwardApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: PhotoAwardApi

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PhotoAwardApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getPhotoAwardList should parse successfully`() = runTest {
        // Given
        val responseBody = """
            {
              "response": {
                "header": {
                  "resultCode": "0000",
                  "resultMsg": "OK"
                },
                "body": {
                  "items": {
                    "item": [
                      {
                        "contentId": "Avrggd",
                        "koTitle": "하늘을 걷는 시간"
                      }
                    ]
                  },
                  "numOfRows": 1,
                  "pageNo": 1,
                  "totalCount": 16
                }
              }
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // When
        val result = api.getPhotoAwardList()

        // Then
        assertEquals("0000", result.response.header.resultCode)
        assertEquals(1, result.response.body.items?.item?.size)
        assertEquals("하늘을 걷는 시간", result.response.body.items?.item?.first()?.koTitle)
    }

    @Test
    fun `getGalleryList should parse successfully`() = runTest {
        // Given
        val responseBody = """
            {
              "response": {
                "header": {
                  "resultCode": "0000",
                  "resultMsg": "OK"
                },
                "body": {
                  "items": {
                    "item": [
                      {
                        "galContentId": "1925644",
                        "galContentTypeId": "17",
                        "galTitle": "장보고동상",
                        "galWebImageUrl": "https://example.com/image.jpg",
                        "galCreatedtime": "20140613193744",
                        "galModifiedtime": "20260325152648"
                      }
                    ]
                  },
                  "numOfRows": 1,
                  "pageNo": 1,
                  "totalCount": 6119
                }
              }
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // When
        val result = api.getGalleryList()

        // Then
        assertEquals("0000", result.response.header.resultCode)
        assertEquals(1, result.response.body.items?.item?.size)
        assertEquals("장보고동상", result.response.body.items?.item?.first()?.galTitle)
    }
}
