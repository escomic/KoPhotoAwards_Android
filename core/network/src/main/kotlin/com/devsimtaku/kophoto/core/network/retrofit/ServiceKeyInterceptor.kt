package com.devsimtaku.kophoto.core.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 모든 요청에 공공데이터 서비스 키를 쿼리 파라미터로 추가하는 인터셉터
 */
internal class ServiceKeyInterceptor(private val serviceKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("serviceKey", serviceKey)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
