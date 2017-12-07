package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.jameskbride.codemashcompanion.R

class MainActivityImpl {

    var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    fun onCreate(savedInstanceState: Bundle?, mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.activity_main)
        val container = mainActivity.findViewById<ViewPager>(R.id.container)
        val toolbar = mainActivity.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = container.resources.getString(R.string.sessions)
        mainActivity.setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(mainActivity.supportFragmentManager)

        container.adapter = mSectionsPagerAdapter

        val tabs = mainActivity.findViewById<TabLayout>(R.id.tabs)
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(MainTabSelectListener(toolbar, container))
    }
}

class MainTabSelectListener constructor(val toolbar: Toolbar, val container:ViewPager): TabLayout.ViewPagerOnTabSelectedListener(container) {
    override fun onTabSelected(tab: TabLayout.Tab?) {
        super.onTabSelected(tab)
        toolbar.title = when(tab?.position) {
            1 -> container.resources.getString(R.string.speakers)
            2 -> container.resources.getString(R.string.schedule)
            else -> container.resources.getString(R.string.sessions)
        }
    }
}