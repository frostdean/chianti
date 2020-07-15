package com.wmjun.chianti.presentation.controllers.common.dto

import com.fasterxml.jackson.annotation.JsonValue

data class ChiantiResponse<T>(val status: Status,
                              val data: T? = null,
                              val message: String? = null,
                              val meta: ResponseMeta? = null) {

    companion object {
        fun <T> success(data: T? = null, msg: String? = null, meta: ResponseMeta? = null) = ChiantiResponse(Status.SUCCESS, data, msg, meta)
    }

    enum class Status(@JsonValue val code: Int) {
        SUCCESS(0),
    }
}
