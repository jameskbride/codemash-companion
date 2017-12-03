package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import javax.inject.Inject

class SpeakersFragmentImpl @Inject constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter,
                                               val speakersViewAdapterFactory:SpeakersViewAdapterFactory = SpeakersViewAdapterFactory()): SpeakersFragmentView {

    private lateinit var speakersView: GridView
    private lateinit var speakersViewAdapter: SpeakersViewAdapter

    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, speakersFragment: SpeakersFragment): View? {
        val view = inflater?.inflate(R.layout.fragment_speakers, container, false)
        speakersFragmentPresenter.view = this
        speakersView = view!!.findViewById(R.id.speakers)
        speakersViewAdapter = speakersViewAdapterFactory.make(speakersFragment.context)
        speakersView.adapter = speakersViewAdapter

        return view
    }

    fun onResume() {
        speakersFragmentPresenter.requestSpeakerData()
    }

    fun onPause() {
    }

    override fun onSpeakerDataRetrieved(speakers: Array<Speaker>) {
        speakersViewAdapter.setSpeakers(speakers)
    }
}