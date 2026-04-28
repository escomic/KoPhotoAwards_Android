package com.devsimtaku.kophoto.core.domain.model

import java.io.IOException

/**
 * API 비즈니스 로직 에러를 처리하기 위한 커스텀 예외 클래스
 * OkHttp Interceptor 및 Retrofit에서 안전하게 처리되도록 IOException을 상속받습니다.
 */
class KoPhotoApiException(
    val errorCode: KoPhotoErrorCode,
    override val message: String
) : IOException("[$errorCode] $message")
