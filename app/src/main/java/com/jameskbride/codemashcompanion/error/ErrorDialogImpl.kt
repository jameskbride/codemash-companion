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

class ErrorDialogImpl @Inject constructor(val presenter: ErrorDialogPresenter) {
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, qtn: ErrorDialog): View? {
        val view = inflater.inflate(R.layout.error_dialog, container, true)

        val errorDialogParams = qtn.arguments?.getSerializable(PARAMETER_BLOCK) as ErrorDialogParams

        view.findViewById<TextView>(R.id.error_dialog_title).setText(errorDialogParams.title)
        view.findViewById<TextView>(R.id.error_dialog_message).setText(errorDialogParams.message)
        view.findViewById<TextView>(R.id.error_dialog_ok).setOnClickListener {view: View? ->
            qtn.dismiss()
            presenter.navigateToMain()
        }

        return view
    }
}

data class ErrorDialogParams constructor(@StringRes val title:Int, @StringRes val message:Int): Serializable

val PARAMETER_BLOCK = "PARAMETER_BLOCK"