package com.jameskbride.codemashcompanion.utils

import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class FabricWrapper {

    fun with(context: Context, crashlytics: Crashlytics) {
        Fabric.with(context)
    }
}