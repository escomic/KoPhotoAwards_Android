package com.devsimtaku.kophoto.core.network.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class KoPhotoResponse<T>(
    @SerialName("response")
    val response: KoPhotoResponseBodyContainer<T>
)

@Serializable
data class KoPhotoResponseBodyContainer<T>(
    @SerialName("header")
    val header: KoPhotoHeader,
    @SerialName("body")
    val body: T
)

@Serializable
data class KoPhotoHeader(
    @SerialName("resultCode")
    val resultCode: String,
    @SerialName("resultMsg")
    val resultMsg: String
)

@Serializable
data class KoPhotoListBody<T>(
    @SerialName("items")
    @Serializable(with = KoPhotoItemsSerializer::class)
    val items: KoPhotoItems<T>? = null,
    @SerialName("numOfRows")
    val numOfRows: Int,
    @SerialName("pageNo")
    val pageNo: Int,
    @SerialName("totalCount")
    val totalCount: Int
)

@Serializable
data class KoPhotoItems<T>(
    @SerialName("item")
    val item: List<T> = emptyList()
)

class KoPhotoItemsSerializer<T>(tSerializer: KSerializer<T>) :
    JsonTransformingSerializer<KoPhotoItems<T>>(KoPhotoItems.serializer(tSerializer)) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element is JsonPrimitive && element.isString && element.content.isEmpty()) {
            JsonObject(emptyMap())
        } else {
            element
        }
    }
}
