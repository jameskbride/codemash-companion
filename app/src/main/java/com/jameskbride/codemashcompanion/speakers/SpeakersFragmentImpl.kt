package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import javax.inject.Inject

class SpeakersFragmentImpl @Inject constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter): SpeakersFragmentView {

    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {
        inflater?.inflate(R.layout.fragment_speakers, container, false)
        speakersFragmentPresenter.view = this
    }

    fun onResume() {
        speakersFragmentPresenter.open()
        speakersFragmentPresenter.requestSpeakerData()
    }

    fun onPause() {
        speakersFragmentPresenter.close()
    }

    override fun onSpeakerDataRetrieved(speakers: Array<Speaker>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}