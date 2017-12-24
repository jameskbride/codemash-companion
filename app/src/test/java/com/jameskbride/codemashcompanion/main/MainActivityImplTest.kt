package com.jameskbride.codemashcompanion.main

import android.content.Intent
import android.content.res.Resources
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.about.AboutActivity
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class MainActivityImplTest {

    @Mock private lateinit var mainActivity: MainActivity
    @Mock private lateinit var supportFragmentManager: FragmentManager
    @Mock private lateinit var container: ViewPager
    @Mock private lateinit var tabs: TabLayout
    @Mock private lateinit var toolbar: Toolbar
    @Mock private lateinit var resources: Resources
    @Mock private lateinit var menuInflater:MenuInflater
    @Mock private lateinit var menu:Menu
    @Mock private lateinit var menuItem:MenuItem
    @Mock private lateinit var intent:Intent
    @Mock private lateinit var intentFactory:IntentFactory

    private lateinit var subject: MainActivityImpl

    private val sessions = "sessions"
    private val speakers = "speakers"
    private val schedule = "schedule"

    @Before
    fun setUp() {
        initMocks(this)

        subject = MainActivityImpl(intentFactory)

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

    @Test
    fun onCreateOptionsMenuInflatesTheMenu() {
        subject.onCreate(null, mainActivity)

        whenever(mainActivity.menuInflater).thenReturn(menuInflater)

        val result = subject.onCreateOptionsMenu(menu, mainActivity)

        assertTrue(result)
        verify(menuInflater).inflate(R.menu.menu_main, menu)
    }

    @Test
    fun onOptionsItemSelectedNavigatesToAbout() {
        whenever(menuItem.itemId).thenReturn(R.id.action_about)
        subject.onCreate(null, mainActivity)

        whenever(intentFactory.make(mainActivity, AboutActivity::class.java)).thenReturn(intent)

        val result = subject.onOptionsItemSelected(menuItem, mainActivity)

        assertTrue(result)
        verify(mainActivity).startActivity(intent)
    }
}