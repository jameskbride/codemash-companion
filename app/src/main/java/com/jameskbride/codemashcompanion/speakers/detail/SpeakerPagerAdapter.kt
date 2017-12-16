package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.jameskbride.codemashcompanion.data.model.Speaker
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailFragment.Companion.SPEAKER_KEY

class SpeakerPagerAdapter constructor(fm: FragmentManager, val speakerPagerAdapterImpl:SpeakerPagerAdapterImpl = SpeakerPagerAdapterImpl()) : FragmentStatePagerAdapter(fm) {

    init {
        speakerPagerAdapterImpl.speakerPagerAdapter = this
    }

    override fun getItem(position: Int): Fragment {
        return speakerPagerAdapterImpl.getItem(position)
    }

    override fun getCount(): Int {
        return speakerPagerAdapterImpl.getCount()
    }

    fun updateSpeakers(speakers: Array<Speaker>) {
        speakerPagerAdapterImpl.updateSpeakers(speakers)
    }
}

class SpeakerPagerAdapterImpl {

    lateinit var speakerPagerAdapter:SpeakerPagerAdapter
    var speakers:Array<Speaker> = arrayOf()

    fun getItem(position: Int): Fragment {
        val speakerDetailFragment = SpeakerDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable(SPEAKER_KEY, speakers[position])
        speakerDetailFragment.arguments = bundle

        return speakerDetailFragment
    }

    fun getCount(): Int {
        return speakers.size
    }

    fun updateSpeakers(speakers: Array<Speaker>) {
        this.speakers = speakers
        speakerPagerAdapter.notifyDataSetChanged()
    }
}

class SpeakerPagerAdapterFactory {
    fun make(fragmentManager: FragmentManager): SpeakerPagerAdapter {
        return SpeakerPagerAdapter(fragmentManager)
    }
}