package com.smtm.pickle

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.kakao.sdk.common.KakaoSdk
import timber.log.Timber

@HiltAndroidApp
class PickleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
