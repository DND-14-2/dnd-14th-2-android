package com.smtm.pickle.data.common.utils

import org.json.JSONObject
import retrofit2.HttpException

suspend inline fun <T> apiCall(crossinline block: suspend () -> T): T {
    return try {
        block()
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val message = parseMessageFromJson(errorBody)
        throw Exception(message, e)
    }
}

fun parseMessageFromJson(jsonString: String?): String? {
    if (jsonString.isNullOrBlank()) return null
    return try {
        val jsonObject = JSONObject(jsonString)
        jsonObject.optString("message").takeIf { it.isNotBlank() }
    } catch (_: Exception) {
        null
    }
}
