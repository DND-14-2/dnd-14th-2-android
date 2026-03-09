package com.smtm.pickle.presentation.common.extension

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import timber.log.Timber

fun Context.sendSms(message: String) {
    try {
        Intent(Intent.ACTION_SENDTO).apply {
            data = "smsto:".toUri()
            putExtra("sms_body", message)
        }.let(::startActivity)
    } catch (e: Exception) {
        Timber.e(e, "문자 전송 시도 실패")
    }
}
