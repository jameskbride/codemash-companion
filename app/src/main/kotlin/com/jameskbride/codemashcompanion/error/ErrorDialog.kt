package com.jameskbride.codemashcompanion.error

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class ErrorDialog: androidx.fragment.app.DialogFragment() {

    @Inject
    lateinit var impl:ErrorDialogImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        CodemashCompanionApplication.applicationComponent.inject(this)
        return impl.onCreateView(inflater, container, savedInstanceState, this)
    }
}

class ErrorDialogFactory {
    fun make(): ErrorDialog {
        return ErrorDialog()
    }
}