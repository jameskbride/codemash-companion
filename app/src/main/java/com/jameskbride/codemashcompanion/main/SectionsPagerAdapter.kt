package com.jameskbride.codemashcompanion.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jameskbride.codemashcompanion.schedule.ScheduleFragment
import com.jameskbride.codemashcompanion.sessions.SessionsFragment
import com.jameskbride.codemashcompanion.speakers.SpeakersFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> SessionsFragment()
            1 -> SpeakersFragment()
            else -> ScheduleFragment()
        }
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}