package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakersParams
import com.jameskbride.codemashcompanion.speakers.list.SpeakersRecyclerViewAdapter
import com.jameskbride.codemashcompanion.speakers.list.SpeakersViewAdapterFactory
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.Toaster
import javax.inject.Inject

class SpeakersFragmentImpl @Inject constructor(val presenter: SpeakersFragmentPresenter,
                                               val speakersViewAdapterFactory: SpeakersViewAdapterFactory = SpeakersViewAdapterFactory(),
                                               val intentFactory:IntentFactory = IntentFactory(),
                                               val toaster:Toaster = Toaster()): SpeakersFragmentView {
    private lateinit var speakersView: RecyclerView

    private lateinit var qtn:SpeakersFragment
    private lateinit var speakersViewAdapter: SpeakersRecyclerViewAdapter

    private lateinit var speakersRefresh: SwipeRefreshLayout
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, qtn: SpeakersFragment): View? {
        this.qtn = qtn
        val view = inflater?.inflate(R.layout.fragment_speakers, container, false)
        presenter.view = this
        speakersView = view!!.findViewById(R.id.speakers)
        speakersView.layoutManager = qtn.makeGridLayoutManager(2)
        speakersView.isDrawingCacheEnabled = true
        speakersView.setItemViewCacheSize(20)
        speakersView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        speakersViewAdapter = speakersViewAdapterFactory.make(presenter)
        speakersView.adapter = speakersViewAdapter

        speakersRefresh = view.findViewById(R.id.speakers_refresh)
        speakersRefresh.setOnRefreshListener {
            speakersRefresh.isRefreshing = true
            presenter.refreshConferenceData()
        }

        return view
    }

    override fun stopRefreshing() {
        speakersRefresh.isRefreshing = false
    }

    override fun displayErrorMessage(message: Int) {
        toaster.makeText(qtn.activity!!, R.string.could_not_refresh, Toast.LENGTH_SHORT).show()
    }

    fun onResume() {
        presenter.open()
        presenter.requestSpeakerData()
    }

    fun onPause() {
        presenter.close()
    }

    override fun navigateToDetails(speakers: Array<FullSpeaker>, index: Int) {
        val intent = intentFactory.make(qtn.context, SpeakerDetailActivity::class.java)
        intent.putExtra(PARAMETER_BLOCK, SpeakersParams(speakers, index))

        qtn.activity!!.startActivity(intent)
    }

    override fun onSpeakerDataRetrieved(speakers: Array<FullSpeaker>) {
        configureColumns(speakers)
        speakersViewAdapter.setSpeakers(speakers)
        speakersRefresh.isRefreshing = false
    }

    private fun configureColumns(speakers: Array<FullSpeaker>) {
        if (speakers.isEmpty()) {
            speakersView.layoutManager = qtn.makeGridLayoutManager(1)
        } else {
            speakersView.layoutManager = qtn.makeGridLayoutManager(2)
        }
    }
}