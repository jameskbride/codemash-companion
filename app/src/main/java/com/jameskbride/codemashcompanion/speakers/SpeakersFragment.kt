package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication

class SpeakersFragment: Fragment() {

    lateinit var impl: SpeakersFragmentImpl

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        impl.onResume()
    }

    override fun onPause() {
        super.onPause()
        impl.onPause()
    }
}