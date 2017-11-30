package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import javax.inject.Inject

class SpeakersFragmentImpl @Inject constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter) {
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {
        inflater?.inflate(R.layout.fragment_speakers, container, false)
    }

    fun onResume() {
        speakersFragmentPresenter.open()
        speakersFragmentPresenter.requestSpeakerData()
    }

    fun onPause() {
        speakersFragmentPresenter.close()
    }
}