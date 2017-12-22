package com.jameskbride.codemashcompanion.error

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import java.io.Serializable
import javax.inject.Inject

class ErrorDialogImpl @Inject constructor() {
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, qtn: ErrorDialog): View? {
        val view = inflater.inflate(R.layout.error_dialog, container, false)
        val errorDialogParams = qtn.arguments?.getSerializable(PARAMETER_BLOCK) as ErrorDialogParams
        view.findViewById<TextView>(R.id.error_dialog_title).setText(errorDialogParams.title)
        view.findViewById<TextView>(R.id.error_dialog_message).setText(errorDialogParams.message)

        return view
    }
}

data class ErrorDialogParams constructor(@StringRes val title:Int, @StringRes val message:Int): Serializable

val PARAMETER_BLOCK = "PARAMETER_BLOCK"