package com.jameskbride.codemashcompanion.main

import android.content.res.Resources
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MainActivityImplTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var supportFragmentManager: FragmentManager
    private lateinit var container: ViewPager
    private lateinit var tabs: TabLayout
    private lateinit var toolbar: Toolbar
    private lateinit var resources: Resources

    private lateinit var subject: MainActivityImpl

    private val sessions = "sessions"
    private val speakers = "speakers"
    private val schedule = "schedule"

    @Before
    fun setUp() {
        mainActivity = mock()
        supportFragmentManager = mock()
        container = mock()
        tabs = mock()
        toolbar = mock()
        resources = mock()

        subject = MainActivityImpl()

        whenever(mainActivity.findViewById<ViewPager>(R.id.container)).thenReturn(container)
        whenever(mainActivity.findViewById<TabLayout>(R.id.tabs)).thenReturn(tabs)
        whenever(mainActivity.findViewById<Toolbar>(R.id.toolbar)).thenReturn(toolbar)
        whenever(mainActivity.supportFragmentManager).thenReturn(supportFragmentManager)
        whenever(container.resources).thenReturn(resources)
        whenever(resources.getString(R.string.sessions)).thenReturn(sessions)
        whenever(resources.getString(R.string.speakers)).thenReturn(speakers)
        whenever(resources.getString(R.string.schedule)).thenReturn(schedule)
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
        verify(toolbar).setTitle(sessions)
    }

    @Test
    fun onCreateConfiguresTheViewPager() {
        subject.onCreate(null, mainActivity)

        assertNotNull(subject.mSectionsPagerAdapter)
        verify(container).setAdapter(subject.mSectionsPagerAdapter)
        verify(container).addOnPageChangeListener(any())
        verify(tabs).addOnTabSelectedListener(any())
    }

    @Test
    fun onCreateConfiguresTabSelectedListenerToUpdateTheToolbarTextWhenSessionsTabSelected() {
        subject.onCreate(null, mainActivity)

        val mainTabSelectedListenerCaptor = argumentCaptor<TabLayout.ViewPagerOnTabSelectedListener>()
        verify(tabs).addOnTabSelectedListener(mainTabSelectedListenerCaptor.capture())

        val tabSelectedListener = mainTabSelectedListenerCaptor.firstValue
        val tab = mock<TabLayout.Tab>()
        whenever(tab.position).thenReturn(0)
        reset(toolbar)
        tabSelectedListener.onTabSelected(tab)

        verify(toolbar).setTitle(sessions)
    }

    @Test
    fun onCreateConfiguresTabSelectedListenerToUpdateTheToolbarTextWhenSpeakersTabSelected() {
        subject.onCreate(null, mainActivity)

        val mainTabSelectedListenerCaptor = argumentCaptor<TabLayout.ViewPagerOnTabSelectedListener>()
        verify(tabs).addOnTabSelectedListener(mainTabSelectedListenerCaptor.capture())

        val tabSelectedListener = mainTabSelectedListenerCaptor.firstValue
        val tab = mock<TabLayout.Tab>()
        whenever(tab.position).thenReturn(1)
        tabSelectedListener.onTabSelected(tab)

        verify(toolbar).setTitle(speakers)
    }

    @Test
    fun onCreateConfiguresTabSelectedListenerToUpdateTheToolbarTextWhenScheduleTabSelected() {
        subject.onCreate(null, mainActivity)

        val mainTabSelectedListenerCaptor = argumentCaptor<TabLayout.ViewPagerOnTabSelectedListener>()
        verify(tabs).addOnTabSelectedListener(mainTabSelectedListenerCaptor.capture())

        val tabSelectedListener = mainTabSelectedListenerCaptor.firstValue
        val tab = mock<TabLayout.Tab>()
        whenever(tab.position).thenReturn(2)
        tabSelectedListener.onTabSelected(tab)

        verify(toolbar).setTitle(schedule)
    }
}