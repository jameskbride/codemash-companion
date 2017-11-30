package com.jameskbride.codemashcompanion.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jameskbride.codemashcompanion.speakers.SpeakersFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when(position) {
            0 -> PlaceholderFragment.newInstance(0)
            1 -> SpeakersFragment()
            else -> PlaceholderFragment.newInstance(2)
        }
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}