package com.devsimtaku.kophoto.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoAwardItem(
    @SerialName("contentId")
    val contentId: String,
    @SerialName("koTitle")
    val koTitle: String,
    @SerialName("enTitle")
    val enTitle: String? = null,
    @SerialName("lDongRegnCd")
    val lDongRegnCd: String? = null,
    @SerialName("koFilmst")
    val koFilmst: String? = null,
    @SerialName("enFilmst")
    val enFilmst: String? = null,
    @SerialName("filmDay")
    val filmDay: String? = null,
    @SerialName("koCmanNm")
    val koCmanNm: String? = null,
    @SerialName("enCmanNm")
    val enCmanNm: String? = null,
    @SerialName("koWnprzDiz")
    val koWnprzDiz: String? = null,
    @SerialName("enWnprzDiz")
    val enWnprzDiz: String? = null,
    @SerialName("koKeyWord")
    val koKeyWord: String? = null,
    @SerialName("enKeyWord")
    val enKeyWord: String? = null,
    @SerialName("orgImage")
    val orgImage: String? = null,
    @SerialName("thumbImage")
    val thumbImage: String? = null,
    @SerialName("cpyrhtDivCd")
    val cpyrhtDivCd: String? = null,
    @SerialName("regDt")
    val regDt: String? = null,
    @SerialName("mdfcnDt")
    val mdfcnDt: String? = null
)
