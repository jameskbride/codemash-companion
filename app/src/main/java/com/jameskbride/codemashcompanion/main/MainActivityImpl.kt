package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.jameskbride.codemashcompanion.R

class MainActivityImpl {

    var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    fun onCreate(savedInstanceState: Bundle?, mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.activity_main)
        mainActivity.setSupportActionBar(mainActivity.findViewById(R.id.toolbar))
        mSectionsPagerAdapter = SectionsPagerAdapter(mainActivity.supportFragmentManager)

        val container = mainActivity.findViewById<ViewPager>(R.id.container)
        container.adapter = mSectionsPagerAdapter

        val tabs = mainActivity.findViewById<TabLayout>(R.id.tabs)
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }
}