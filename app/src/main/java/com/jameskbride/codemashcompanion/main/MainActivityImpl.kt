package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.jameskbride.codemashcompanion.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityImpl {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    fun onCreate(savedInstanceState: Bundle?, mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.activity_main)

        mainActivity.setSupportActionBar(mainActivity.toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(mainActivity.supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mainActivity.container.adapter = mSectionsPagerAdapter

        mainActivity.container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mainActivity.tabs))
        mainActivity.tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mainActivity.container))
    }
}