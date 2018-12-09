package com.jameskbride.codemashcompanion.utils

import android.content.Context
import android.content.Intent

class IntentFactory {

    fun make(context: Context?, clazz:Class<out androidx.fragment.app.FragmentActivity>):Intent {
        return Intent(context, clazz)
    }

    fun make(intent:String):Intent {
        return Intent(intent)
    }
}