package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import javax.inject.Inject

class SpeakersFragmentImpl @Inject constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter, val speakersViewAdapterFactory: SpeakersViewAdapterFactory = SpeakersViewAdapterFactory()): SpeakersFragmentView {

    private lateinit var speakersView: RecyclerView
    private lateinit var speakersViewAdapter: SpeakersRecyclerViewAdapter

    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, speakersFragment: SpeakersFragment): View? {
        val view = inflater?.inflate(R.layout.fragment_speakers, container, false)
        speakersFragmentPresenter.view = this
        speakersView = view!!.findViewById(R.id.speakers)
        speakersView.layoutManager = speakersFragment.makeGridLayoutManager(2)
        speakersView.isDrawingCacheEnabled = true
        speakersView.setItemViewCacheSize(20)
        speakersView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        speakersViewAdapter = speakersViewAdapterFactory.make()
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