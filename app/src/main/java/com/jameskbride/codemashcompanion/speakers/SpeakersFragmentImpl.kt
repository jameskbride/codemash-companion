package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailParams
import com.jameskbride.codemashcompanion.utils.IntentFactory
import javax.inject.Inject

class SpeakersFragmentImpl @Inject constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter,
                                               val speakersViewAdapterFactory: SpeakersViewAdapterFactory = SpeakersViewAdapterFactory(),
                                               val intentFactory:IntentFactory = IntentFactory()): SpeakersFragmentView {
    private lateinit var speakersView: RecyclerView
    private lateinit var qtn:SpeakersFragment

    private lateinit var speakersViewAdapter: SpeakersRecyclerViewAdapter
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, qtn: SpeakersFragment): View? {
        this.qtn = qtn
        val view = inflater?.inflate(R.layout.fragment_speakers, container, false)
        speakersFragmentPresenter.view = this
        speakersView = view!!.findViewById(R.id.speakers)
        speakersView.layoutManager = qtn.makeGridLayoutManager(2)
        speakersView.isDrawingCacheEnabled = true
        speakersView.setItemViewCacheSize(20)
        speakersView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        speakersViewAdapter = speakersViewAdapterFactory.make(speakersFragmentPresenter)
        speakersView.adapter = speakersViewAdapter

        return view
    }

    fun onResume() {
        speakersFragmentPresenter.requestSpeakerData()
    }

    fun onPause() {
    }

    override fun navigateToDetails(speakers: Array<FullSpeaker>, index: Int) {
        val intent = intentFactory.make(qtn.context, SpeakerDetailActivity::class.java)
        intent.putExtra(PARAMETER_BLOCK, SpeakerDetailParams(speakers, index))

        qtn.activity!!.startActivity(intent)
    }

    override fun onSpeakerDataRetrieved(speakers: Array<FullSpeaker>) {
        speakersViewAdapter.setSpeakers(speakers)
    }
}