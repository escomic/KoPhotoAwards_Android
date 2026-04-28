package com.devsimtaku.kophoto.core.ui.util

import android.content.Context
import com.devsimtaku.kophoto.core.domain.model.KoPhotoApiException
import com.devsimtaku.kophoto.core.ui.R

/**
 * Throwable을 사용자에게 보여줄 에러 메시지로 변환합니다.
 */
fun Throwable.toErrorMessage(context: Context): String {
    return if (this is KoPhotoApiException) {
        "${errorCode.message}\n\n$message"
    } else {
        message ?: context.getString(R.string.core_unknown_error)
    }
}
