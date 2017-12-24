package com.jameskbride.codemashcompanion.about

import android.support.annotation.IdRes
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class AboutActivityImplTest {

    @Mock private lateinit var qtn:AboutActivity
    @Mock private lateinit var toolbar:Toolbar
    @Mock private lateinit var actionBar: ActionBar

    private lateinit var subject:AboutActivityImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = AboutActivityImpl()

        whenever(qtn.findViewById<Toolbar>(R.id.toolbar)).thenReturn(toolbar)
        whenever(qtn.supportActionBar).thenReturn(actionBar)
    }

    @Test
    fun onCreateSetsTheContentView() {
        subject.onCreate(null, qtn)

        verify(qtn).setContentView(R.layout.activity_about)
    }

    @Test
    fun onCreateConfiguresTheUpNavigation() {
        subject.onCreate(null, qtn)

        verify(qtn).setSupportActionBar(toolbar)
        verify(actionBar).setDisplayHomeAsUpEnabled(true)
    }

    @Test
    fun itCanNavigateUpWhenHomeIsSelected() {
        val menuItem = mock<MenuItem>()
        whenever(menuItem.itemId).thenReturn(android.R.id.home)

        val result = subject.onOptionsItemSelected(menuItem, qtn)

        assertTrue(result)
        verify(qtn).onBackPressed()
    }

    @Test
    fun itNavigatesAccordingToSuperWhenHomeIsNotSelected() {
        val menuItem = mock<MenuItem>()
        @IdRes val someInt = 42
        whenever(menuItem.itemId).thenReturn(someInt)

        subject.onOptionsItemSelected(menuItem, qtn)

        verify(qtn).callSuperOnOptionsItemSelected(menuItem)
    }
}