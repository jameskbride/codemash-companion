package com.jameskbride.codemashcompanion.utils

import com.crashlytics.android.Crashlytics

class CrashlyticsFactory {

    fun make():Crashlytics {
        return Crashlytics()
    }
}