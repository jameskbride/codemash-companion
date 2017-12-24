package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.about.AboutActivity
import com.jameskbride.codemashcompanion.utils.IntentFactory


class MainActivityImpl constructor(val intentFactory: IntentFactory = IntentFactory()) {

    var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private lateinit var mainActivity: MainActivity

    fun onCreate(savedInstanceState: Bundle?, mainActivity: MainActivity) {
        this.mainActivity = mainActivity
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

    fun onCreateOptionsMenu(menu: Menu?, mainActivity: MainActivity): Boolean {
        val inflater = mainActivity.getMenuInflater()
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun onOptionsItemSelected(item: MenuItem, mainActivity: MainActivity): Boolean {
        return when (item.getItemId()) {
            R.id.action_about -> {
                navigateToAbout()
                true
            }
            else -> mainActivity.onSuperOptionsItemSelected(item)
        }
    }

    private fun navigateToAbout() {
        val intent = intentFactory.make(mainActivity, AboutActivity::class.java)
        mainActivity.startActivity(intent)
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