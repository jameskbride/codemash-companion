package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.jameskbride.codemashcompanion.R

class SpeakerDetailActivityImpl(val speakerPagerAdapterFactory: SpeakerPagerAdapterFactory = SpeakerPagerAdapterFactory()) {

    lateinit var speakerDetailPagerAdapter: SpeakerPagerAdapter

    fun onCreate(savedInstanceState: Bundle?, activity: SpeakerDetailActivity) {
        activity.setContentView(R.layout.activity_speaker_detail)

        val speakerDetailParams:SpeakerDetailParams =
                activity.intent.getSerializableExtra(PARAMETER_BLOCK) as SpeakerDetailParams
        speakerDetailPagerAdapter =
                speakerPagerAdapterFactory.make(activity.supportFragmentManager, speakerDetailParams.speakers)

        val container = activity.findViewById<ViewPager>(R.id.speaker_pager)
        container.adapter = speakerDetailPagerAdapter
        container.currentItem = speakerDetailParams.index
    }

    companion object {
        val PARAMETER_BLOCK:String = "PARAMETER_BLOCK"
    }
}