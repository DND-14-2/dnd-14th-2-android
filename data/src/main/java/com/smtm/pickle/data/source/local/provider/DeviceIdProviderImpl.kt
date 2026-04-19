package com.smtm.pickle.data.source.local.provider

import android.content.Context
import android.provider.Settings
import com.smtm.pickle.domain.provider.DeviceIdProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceIdProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DeviceIdProvider {

    override suspend fun getOrCreate(): String {
        // ANDROID_ID: 앱 재설치 후에도 동일 기기에서 동일한 값 유지 (팩토리 리셋 시에만 변경)
        // ANDROID_ID를 사용할 수 없는 기기에서는 데모 로그인을 지원하지 않음
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return androidId.takeIf { !it.isNullOrEmpty() }
            ?: error("지원하지 않는 기기")
    }
}
