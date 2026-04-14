package com.smtm.pickle.data.source.local.provider

import android.content.Context
import android.provider.Settings
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smtm.pickle.data.di.Preference
import com.smtm.pickle.domain.provider.DeviceIdProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceIdProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @Preference private val dataStore: DataStore<Preferences>,
) : DeviceIdProvider {

    private val DEMO_DEVICE_ID = stringPreferencesKey("demo_device_id")

    override suspend fun getOrCreate(): String {
        // ANDROID_ID: 앱 재설치 후에도 동일 기기에서 동일한 값 유지 (팩토리 리셋 시에만 변경)
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        if (!androidId.isNullOrEmpty()) return androidId

        // Fallback: ANDROID_ID를 사용할 수 없는 경우 (일부 에뮬레이터 등)
        val existing = dataStore.data.first()[DEMO_DEVICE_ID]
        if (existing != null) return existing

        val newId = UUID.randomUUID().toString()
        dataStore.edit { it[DEMO_DEVICE_ID] = newId }
        return newId
    }
}
