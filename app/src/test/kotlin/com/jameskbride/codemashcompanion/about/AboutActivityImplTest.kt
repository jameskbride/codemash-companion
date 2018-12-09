package com.jameskbride.codemashcompanion.about

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.jameskbride.codemashcompanion.BuildConfig
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.UriWrapper
import com.nhaarman.mockito_kotlin.argumentCaptor
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
    @Mock private lateinit var intentFactory:IntentFactory
    @Mock private lateinit var intent:Intent
    @Mock private lateinit var uri: Uri
    @Mock private lateinit var githubBlock:LinearLayout
    @Mock private lateinit var uriWrapper:UriWrapper
    @Mock private lateinit var resources:Resources
    @Mock private lateinit var versionNumber:TextView

    private lateinit var subject:AboutActivityImpl

    val urlString = "url"

    @Before
    fun setUp() {
        initMocks(this)

        subject = AboutActivityImpl(intentFactory, uriWrapper)

        whenever(qtn.findViewById<Toolbar>(R.id.toolbar)).thenReturn(toolbar)
        whenever(qtn.supportActionBar).thenReturn(actionBar)
        whenever(qtn.findViewById<LinearLayout>(R.id.github_block)).thenReturn(githubBlock)
        whenever(qtn.findViewById<TextView>(R.id.version_number)).thenReturn(versionNumber)
        whenever(qtn.resources).thenReturn(resources)
        whenever(resources.getString(R.string.codemash_companion_url)).thenReturn(urlString)
        whenever(uriWrapper.parse(urlString)).thenReturn(uri)
    }

    @Test
    fun onCreateSetsTheContentView() {
        subject.onCreate(null, qtn)

        verify(qtn).setContentView(R.layout.activity_about)
    }

    @Test
    fun onCreateSetsTheTitle() {
        subject.onCreate(null, qtn)

        verify(toolbar).setTitle(R.string.action_about)
    }

    @Test
    fun itSetsTheVersionNumber() {
        subject.onCreate(null, qtn)

        verify(versionNumber).setText(BuildConfig.VERSION_NAME)
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

    @Test
    fun clickingOnGithubLinkLaunchesAnActivity() {
        whenever(intentFactory.make(Intent.ACTION_VIEW)).thenReturn(intent)
        subject.onCreate(null, qtn)

        val onClickListenerCaptor = argumentCaptor<View.OnClickListener>()
        verify(githubBlock).setOnClickListener(onClickListenerCaptor.capture())
        onClickListenerCaptor.firstValue.onClick(null)

        verify(intentFactory).make(Intent.ACTION_VIEW)
        verify(intent).setData(uri)
        verify(qtn).startActivity(intent)
    }
}