package com.mapbox.services.android.navigation.v5.internal.accounts

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.mapbox.android.accounts.v1.AccountsConstants.DEFAULT_TOKEN_MANAGE_SKU
import com.mapbox.android.accounts.v1.AccountsConstants.KEY_META_DATA_MANAGE_SKU
import timber.log.Timber

internal class Billing private constructor() {

    enum class BillingModel {
        TRIPS,
        MAU
    }

    companion object {
        private var INSTANCE: Billing? = null
        private var billingType = BillingModel.TRIPS

        fun getInstance(context: Context): Billing =
                INSTANCE ?: synchronized(this) {
                    Billing().also { billing ->
                        INSTANCE = billing
                        init(context)
                    }
                }

        private fun getApplicationInfo(context: Context): ApplicationInfo? {
            var applicationInfo: ApplicationInfo? = null
            try {
                applicationInfo = context
                        .packageManager
                        .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            } catch (exception: PackageManager.NameNotFoundException) {
                Timber.e(exception)
            }
            return applicationInfo
        }

        private fun setBillingType(context: Context) {
            val applicationInfo = getApplicationInfo(context)
            applicationInfo?.let { appInfo ->
                appInfo.metaData?.let { metadata ->
                    billingType = when (metadata.getBoolean(KEY_META_DATA_MANAGE_SKU, DEFAULT_TOKEN_MANAGE_SKU)) {
                        true -> BillingModel.TRIPS
                        else -> BillingModel.MAU
                    }
                }
            }
        }

        private fun init(context: Context) {
            setBillingType(context)
        }
    }

    fun getBillingType(): BillingModel {
        return billingType
    }
}
