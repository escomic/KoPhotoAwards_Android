package com.devsimtaku.kophoto.core.domain.model

/**
 * 공공데이터 API 응답 코드 정의
 */
enum class KoPhotoErrorCode(val code: String, val message: String) {
    NORMAL("00", "정상"),
    NORMAL_ALT("0", "정상"),
    NORMAL_FULL("0000", "정상"),
    APPLICATION_ERROR("01", "어플리케이션 에러"),
    APPLICATION_ERROR_ALT("1", "어플리케이션 에러"),
    DB_ERROR("02", "데이터베이스 에러"),
    DB_ERROR_ALT("2", "데이터베이스 에러"),
    NODATA_ERROR("03", "데이터없음 에러"),
    NODATA_ERROR_ALT("3", "데이터없음 에러"),
    HTTP_ERROR("04", "HTTP 에러"),
    HTTP_ERROR_ALT("4", "HTTP 에러"),
    SERVICETIMEOUT_ERROR("05", "서비스 연결실패 에러"),
    SERVICETIMEOUT_ERROR_ALT("5", "서비스 연결실패 에러"),
    INVALID_REQUEST_PARAMETER_ERROR("10", "잘못된 요청 파라메터 에러"),
    NO_MANDATORY_REQUEST_PARAMETERS_ERROR("11", "필수요청 파라메터가 없음"),
    NO_OPENAPI_SERVICE_ERROR("12", "해당오픈API서비스가없거나폐기됨"),
    SERVICE_ACCESS_DENIED_ERROR("20", "서비스접근거부"),
    TEMPORARILY_DISABLE_THE_SERVICE_KEY_ERROR("21", "일시적으로 사용할 수 없는 서비스 키"),
    LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR("22", "서비스요청제한횟수초과에러"),
    SERVICE_KEY_IS_NOT_REGISTERED_ERROR("30", "등록되지않은서비스키"),
    DEADLINE_HAS_EXPIRED_ERROR("31", "활용기간만료"),
    UNREGISTERED_IP_ERROR("32", "등록되지않은 IP"),
    UNSIGNED_CALL_ERROR("33", "서명되지 않은 호출"),
    UNKNOWN_ERROR("99", "기타에러");

    companion object {
        fun fromCode(code: String): KoPhotoErrorCode {
            return entries.find { it.code == code } ?: UNKNOWN_ERROR
        }
    }
}
