package com.smtm.pickle.domain.common.utils

import kotlinx.coroutines.CancellationException

inline fun <T> runSuspendCatching(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e.parseServerMessage())
    }
}

/** HttpException의 에러 바디에서 서버 메시지를 추출 */
fun Exception.parseServerMessage(): Exception {
    if (this::class.simpleName != "HttpException") return this

    return try {
        val method = this::class.java.getMethod("response")
        val response = method.invoke(this) ?: return this
        val bodyMethod = response::class.java.getMethod("errorBody")
        val errorBody = bodyMethod.invoke(response) ?: return this
        val stringMethod = errorBody::class.java.getMethod("string")
        val bodyString = stringMethod.invoke(errorBody) as? String ?: return this

        val messageRegex = """"message"\s*:\s*"([^"]*)"""".toRegex()
        val matchResult = messageRegex.find(bodyString)
        val serverMessage = matchResult?.groupValues?.get(1)

        if (!serverMessage.isNullOrBlank()) {
            Exception(serverMessage, this)
        } else {
            this
        }
    } catch (_: Exception) {
        this
    }
}
