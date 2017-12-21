package com.jameskbride.codemashcompanion.error

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class ErrorDialog:DialogFragment() {

    @Inject
    lateinit var errorDialogImpl:ErrorDialogImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  super.onCreateView(inflater, container, savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)

        return view
    }
}

class ErrorDialogFactory {
    fun make(): ErrorDialog {
        return ErrorDialog()
    }
}