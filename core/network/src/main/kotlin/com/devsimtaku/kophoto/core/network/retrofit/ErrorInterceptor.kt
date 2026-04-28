package com.devsimtaku.kophoto.core.network.retrofit

import com.devsimtaku.kophoto.core.domain.model.KoPhotoApiException
import com.devsimtaku.kophoto.core.domain.model.KoPhotoErrorCode
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * HTTP 상태 코드를 감시하고 비정상 응답 시 커스텀 예외를 던지는 인터셉터
 */
internal class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            // 네트워크 연결 자체의 실패 (Timeout, No Connection 등)
            throw IOException(e.message ?: "Network connection failed", e)
        }

        if (!response.isSuccessful) {
            val errorCode = when (response.code) {
                401 -> KoPhotoErrorCode.SERVICE_ACCESS_DENIED_ERROR // 인증 실패
                403 -> KoPhotoErrorCode.SERVICE_ACCESS_DENIED_ERROR // 권한 없음
                404 -> KoPhotoErrorCode.NO_OPENAPI_SERVICE_ERROR    // 경로 잘못됨
                429 -> KoPhotoErrorCode.LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR // 요청 초과
                in 500..599 -> KoPhotoErrorCode.HTTP_ERROR // 서버 에러
                else -> KoPhotoErrorCode.UNKNOWN_ERROR
            }
            
            val message = "HTTP ${response.code}: ${response.message}"
            response.close() // 응답 스트림 닫기
            
            throw KoPhotoApiException(
                errorCode = errorCode,
                message = message
            )
        }

        return response
    }
}
