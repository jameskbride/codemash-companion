package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.framework.BaseActivity
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl

class SpeakerDetailActivityImpl(val speakerPagerAdapterFactory: SpeakerPagerAdapterFactory = SpeakerPagerAdapterFactory())
    : BaseActivityImpl() {
    lateinit var speakerDetailPagerAdapter: SpeakerPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?, qtn: BaseActivity) {
        qtn.setContentView(R.layout.activity_speaker_detail)
        setTitle(qtn, R.string.speaker_detail)

        val speakerDetailParams: SpeakersParams =
                qtn.intent.getSerializableExtra(PARAMETER_BLOCK) as SpeakersParams
        speakerDetailPagerAdapter =
                speakerPagerAdapterFactory.make(qtn.supportFragmentManager)
        speakerDetailPagerAdapter.updateSpeakers(speakerDetailParams.speakers)

        val container = qtn.findViewById<ViewPager>(R.id.speaker_pager)
        container.adapter = speakerDetailPagerAdapter
        container.currentItem = speakerDetailParams.index
        qtn.setSupportActionBar(qtn.findViewById(R.id.toolbar))
        qtn.supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onResume(sessionDetailActivity: BaseActivity) {
    }

    override fun onPause(sessionDetailActivity: BaseActivity) {
    }

    fun onOptionsItemSelected(item: MenuItem?, qtn:SpeakerDetailActivity): Boolean {
        when(item?.itemId) {
            android.R.id.home ->  {
                qtn.onBackPressed()
                return true
            }
            else -> return qtn.callSuperOnOptionsItemSelected(item!!)
        }
    }
}