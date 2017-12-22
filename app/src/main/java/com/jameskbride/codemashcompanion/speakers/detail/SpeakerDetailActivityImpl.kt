package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.jameskbride.codemashcompanion.R

class SpeakerDetailActivityImpl(val speakerPagerAdapterFactory: SpeakerPagerAdapterFactory = SpeakerPagerAdapterFactory()) {

    lateinit var speakerDetailPagerAdapter: SpeakerPagerAdapter

    fun onCreate(savedInstanceState: Bundle?, qtn: SpeakerDetailActivity) {
        qtn.setContentView(R.layout.activity_speaker_detail)

        val speakerDetailParams:SpeakerDetailParams =
                qtn.intent.getSerializableExtra(PARAMETER_BLOCK) as SpeakerDetailParams
        speakerDetailPagerAdapter =
                speakerPagerAdapterFactory.make(qtn.supportFragmentManager)
        speakerDetailPagerAdapter.updateSpeakers(speakerDetailParams.speakers)

        val container = qtn.findViewById<ViewPager>(R.id.speaker_pager)
        container.adapter = speakerDetailPagerAdapter
        container.currentItem = speakerDetailParams.index
        qtn.setSupportActionBar(qtn.findViewById(R.id.toolbar))
        qtn.supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    companion object {
        val PARAMETER_BLOCK:String = "PARAMETER_BLOCK"
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