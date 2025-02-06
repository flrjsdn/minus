package com.hexa.muinus.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 700 - ES
 * 701 - MySQL
 */
@Getter
@RequiredArgsConstructor
public enum ESErrorCode implements ErrorCode {
    ES_SYNC_FAILED(70000, "Elasticsearch 데이터 동기화에 실패했습니다."),
    ES_SAVE_ERROR(70001, "Elasticsearch 저장 중 오류가 발생했습니다."),
    ES_CONNECTION_ERROR(70002, "Elasticsearch 서버 연결에 실패했습니다."),
    ES_INDEX_NOT_FOUND(70003, "Elasticsearch 인덱스를 찾을 수 없습니다."),
    ES_DOCUMENT_NOT_FOUND(70004, "Elasticsearch 문서를 찾을 수 없습니다."),
    ES_QUERY_ERROR(70005, "Elasticsearch 쿼리 실행 중 오류가 발생했습니다."),

    ;
    private final int code;
    private final String message;
}
