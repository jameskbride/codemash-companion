package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailFragment.Companion.SPEAKER_KEY

class SpeakerPagerAdapter constructor(fm: androidx.fragment.app.FragmentManager, val speakerPagerAdapterImpl:SpeakerPagerAdapterImpl = SpeakerPagerAdapterImpl()) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {

    init {
        speakerPagerAdapterImpl.speakerPagerAdapter = this
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return speakerPagerAdapterImpl.getItem(position)
    }

    override fun getCount(): Int {
        return speakerPagerAdapterImpl.getCount()
    }

    fun updateSpeakers(speakers: Array<FullSpeaker>) {
        speakerPagerAdapterImpl.updateSpeakers(speakers)
    }
}

class SpeakerPagerAdapterImpl {

    lateinit var speakerPagerAdapter:SpeakerPagerAdapter
    var speakers:Array<FullSpeaker> = arrayOf()

    fun getItem(position: Int): androidx.fragment.app.Fragment {
        val speakerDetailFragment = SpeakerDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable(SPEAKER_KEY, speakers[position])
        speakerDetailFragment.arguments = bundle

        return speakerDetailFragment
    }

    fun getCount(): Int {
        return speakers.size
    }

    fun updateSpeakers(speakers: Array<FullSpeaker>) {
        this.speakers = speakers
        speakerPagerAdapter.notifyDataSetChanged()
    }
}

class SpeakerPagerAdapterFactory {
    fun make(fragmentManager: androidx.fragment.app.FragmentManager): SpeakerPagerAdapter {
        return SpeakerPagerAdapter(fragmentManager)
    }
}