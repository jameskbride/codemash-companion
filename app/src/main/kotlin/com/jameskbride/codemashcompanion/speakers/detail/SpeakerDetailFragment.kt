package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class SpeakerDetailFragment: Fragment() {
    @Inject
    lateinit var impl: SpeakerDetailFragmentImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        CodemashCompanionApplication.applicationComponent.inject(this)
        return impl.onCreate(inflater, container, savedInstanceState, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        impl.onViewCreated(view, savedInstanceState, this)
    }

    companion object {
        val SPEAKER_KEY = "SPEAKER_KEY"
    }
}