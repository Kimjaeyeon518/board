package com.example.board.web.response

import lombok.Getter
import lombok.Setter


@Getter
@Setter
data class SingleResponse<T>(
    var code: String? = null,
    var message: String? = null,
    var data: T? = null
) {
    companion object {
        fun <T> of(code: String?, message: String?, data: T?): SingleResponse<T> {
            return SingleResponse(code, message, data)
        }

        fun <T> of(code: String?, message: String?): SingleResponse<T> {
            return of(code, message, null)
        }

        fun <T> successOf(data: T): SingleResponse<T> {
            return of("S001", "성공", data)
        }

        fun <T> success(): SingleResponse<T> {
            return of("S001", "성공", null)
        }

        fun <T> failOf(data: T): SingleResponse<T> {
            return of("F001", "실패", data)
        }
    }
}