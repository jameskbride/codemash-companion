package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import javax.inject.Inject

class SpeakersFragmentImpl @Inject constructor() {
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?,
                     speakerFragment: SpeakersFragment) {
        inflater?.inflate(R.layout.fragment_speakers, container, false)
    }
}