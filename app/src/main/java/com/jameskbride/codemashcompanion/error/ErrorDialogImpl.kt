package com.jameskbride.codemashcompanion.error

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import java.io.Serializable
import javax.inject.Inject

class ErrorDialogImpl @Inject constructor() {
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, errorDialog: ErrorDialog): View? {
        val view = inflater.inflate(R.layout.error_dialog, container, false)

        return view
    }
}

data class ErrorDialogParams constructor(@StringRes val title:Int, @StringRes val message:Int): Serializable

val PARAMETER_BLOCK = "PARAMETER_BLOCK"