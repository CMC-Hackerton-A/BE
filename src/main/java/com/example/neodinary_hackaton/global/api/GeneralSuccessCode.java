package com.example.neodinary_hackaton.global.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralSuccessCode implements BaseCode {

    GET_SUCCESS(HttpStatus.OK, "COMMON200", "조회에 성공했습니다."),
    POST_SUCCESS(HttpStatus.CREATED, "COMMON201", "생성에 성공했습니다."),
    PATCH_SUCCESS(HttpStatus.OK, "COMMON200", "수정에 성공했습니다."),
    DELETE_SUCCESS(HttpStatus.NO_CONTENT, "COMMON204", "삭제에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
