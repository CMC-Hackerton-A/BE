package com.example.neodinary_hackaton.global.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private T result;

    // 성공한 경우 (result 포함)
    public static <T> ApiResponse<T> onSuccess(BaseCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    // Controller에서 계속 ResponseEntity.status.body 반복 안하게 메서드로 반복 줄임
    public static <T> ResponseEntity<ApiResponse<T>> onSuccessResponse(
            GeneralSuccessCode code,
            T result
    ) {
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onSuccess(code, result));
    }

    // 실패한 경우 (result 포함)
    public static <T> ApiResponse<T> onFailure(BaseCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }

    // Controller에서 계속 ResponseEntity.status.body 반복 안하게 메서드로 반복 줄임
    public static <T> ResponseEntity<ApiResponse<T>> onFailureResponse(
            GeneralErrorCode code,
            T result
    ) {
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, result));
    }
}
