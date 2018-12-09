package com.jameskbride.codemashcompanion.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jameskbride.codemashcompanion.schedule.ScheduleFragment
import com.jameskbride.codemashcompanion.sessions.SessionsFragment
import com.jameskbride.codemashcompanion.speakers.SpeakersFragment

class SectionsPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
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