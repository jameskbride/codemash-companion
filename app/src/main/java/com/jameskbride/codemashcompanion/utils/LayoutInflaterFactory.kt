package com.jameskbride.codemashcompanion.utils

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LayoutInflaterFactory {

    fun inflate(context: Context, @LayoutRes layoutId: Int, viewGroup: ViewGroup): View? {
        return LayoutInflater.from(context).inflate(layoutId, viewGroup)
    }
}