package com.jameskbride.codemashcompanion.speakers.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.jameskbride.codemashcompanion.network.Speaker

class SpeakerPagerAdapter constructor(fm: FragmentManager, speakers: Array<Speaker> = arrayOf()) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class SpeakerPagerAdapterFactory {
    fun make(fragmentManager: FragmentManager, speakers:Array<Speaker>): SpeakerPagerAdapter {
        return SpeakerPagerAdapter(fragmentManager, speakers)
    }
}