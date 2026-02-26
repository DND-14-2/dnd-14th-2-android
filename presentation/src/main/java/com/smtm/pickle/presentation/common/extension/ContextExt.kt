package com.smtm.pickle.presentation.common.extension

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun Context.sendSms(message: String) {
    Intent(Intent.ACTION_SEND).apply {
        data = "smsto:".toUri()
        putExtra("sms_body", message)
    }.let(::startActivity)
}