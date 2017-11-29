package com.jameskbride.codemashcompanion.main

import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.jameskbride.codemashcompanion.R
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class MainActivityImplTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var supportFragmentManager: FragmentManager
    private lateinit var container: ViewPager
    private lateinit var tabs: TabLayout
    private lateinit var toolbar: Toolbar

    private lateinit var subject: MainActivityImpl

    @Before
    fun setUp() {
        mainActivity = mock(MainActivity::class.java)
        supportFragmentManager = mock(FragmentManager::class.java)
        container = mock(ViewPager::class.java)
        tabs = mock(TabLayout::class.java)
        toolbar = mock(Toolbar::class.java)

        subject = MainActivityImpl()

        `when`(mainActivity.findViewById<ViewPager>(R.id.container)).thenReturn(container)
        `when`(mainActivity.findViewById<TabLayout>(R.id.tabs)).thenReturn(tabs)
        `when`(mainActivity.findViewById<Toolbar>(R.id.toolbar)).thenReturn(toolbar)
        `when`(mainActivity.supportFragmentManager).thenReturn(supportFragmentManager)
    }

    @Test
    fun onCreateSetsTheContentView() {
        subject.onCreate(null, mainActivity)

        verify(mainActivity).setContentView(R.layout.activity_main)
    }

    @Test
    fun onCreateSetsToolbar() {
        subject.onCreate(null, mainActivity)

        verify(mainActivity).setSupportActionBar(toolbar)
    }

    @Test
    fun onCreateConfiguresTheViewPager() {
        subject.onCreate(null, mainActivity)

        assertNotNull(subject.mSectionsPagerAdapter)
        verify(container).setAdapter(subject.mSectionsPagerAdapter)
        verify(container).addOnPageChangeListener(any(ViewPager.OnPageChangeListener::class.java))
        verify(tabs).addOnTabSelectedListener(any(TabLayout.ViewPagerOnTabSelectedListener::class.java))
    }
}