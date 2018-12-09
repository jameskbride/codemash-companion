package com.jameskbride.codemashcompanion.utils

import android.content.Context
import android.widget.Toast

class Toaster {

    private lateinit var toast:Toast

    fun makeText(context: Context, message: Int, length: Int): Toaster {
        toast = Toast.makeText(context, message, length)
        return this
    }

    fun show() {
        toast.show()
    }
}