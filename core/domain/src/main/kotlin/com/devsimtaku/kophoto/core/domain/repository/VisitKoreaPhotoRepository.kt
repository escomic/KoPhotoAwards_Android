package com.devsimtaku.kophoto.core.domain.repository

import com.devsimtaku.kophoto.core.domain.model.Photo

interface VisitKoreaPhotoRepository {

    suspend fun getPhotos(): List<Photo>

}