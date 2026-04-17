package com.devsimtaku.kophoto.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotoAwardItem(
    val contentId: String,
    val koTitle: String,
    val enTitle: String? = null,
    val lDongRegnCd: String? = null,
    val koFilmst: String? = null,
    val enFilmst: String? = null,
    val filmDay: String? = null,
    val koCmanNm: String? = null,
    val enCmanNm: String? = null,
    val koWnprzDiz: String? = null,
    val enWnprzDiz: String? = null,
    val koKeyWord: String? = null,
    val enKeyWord: String? = null,
    val orgImage: String? = null,
    val thumbImage: String? = null,
    val cpyrhtDivCd: String? = null,
    val regDt: String? = null,
    val mdfcnDt: String? = null
)
