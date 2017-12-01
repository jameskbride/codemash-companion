package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import javax.inject.Inject

class SpeakersFragmentImpl @Inject constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter,
                                               val speakersViewAdapterFactory:SpeakersViewAdapterFactory = SpeakersViewAdapterFactory()): SpeakersFragmentView {

    private lateinit var speakersView: GridView

    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, speakersFragment: SpeakersFragment) {
        inflater?.inflate(R.layout.fragment_speakers, container, false)
        speakersFragmentPresenter.view = this
        speakersView = speakersFragment.view!!.findViewById(R.id.speakers)
        speakersView.adapter = speakersViewAdapterFactory.make(speakersFragment.activity)
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