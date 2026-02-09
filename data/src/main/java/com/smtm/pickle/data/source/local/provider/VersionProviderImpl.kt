package com.smtm.pickle.data.source.local.provider

import android.content.Context
import com.smtm.pickle.domain.provider.VersionProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class VersionProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : VersionProvider {
    override val versionName: String
        get() = try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "0.0.0"
        } catch (e: Exception) {
            Timber.e(e, "버전 정보 가져오기 실패")
            "0.0.0"
        }
}
