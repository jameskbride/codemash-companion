package com.jameskbride.codemashcompanion.utils

import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentActivity

class IntentFactory {

    fun make(context: Context, clazz:Class<out FragmentActivity>):Intent {
        return Intent(context, clazz)
    }
}