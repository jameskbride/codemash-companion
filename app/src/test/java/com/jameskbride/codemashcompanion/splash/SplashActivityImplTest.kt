package com.jameskbride.codemashcompanion.splash

import android.content.Intent
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.main.MainActivity
import com.jameskbride.codemashcompanion.utils.IntentFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class SplashActivityImplTest {

    private lateinit var splashActivity: SplashActivity
    private lateinit var presenter: SplashActivityPresenter
    private lateinit var subject: SplashActivityImpl
    private lateinit var intentFactory: IntentFactory

    @Before
    fun setUp() {
        splashActivity = mock(SplashActivity::class.java)
        presenter = mock(SplashActivityPresenter::class.java)
        intentFactory = mock(IntentFactory::class.java)
        subject = SplashActivityImpl(presenter, intentFactory)

        subject.onCreate(null, splashActivity)
    }

    @Test
    fun onCreateSetsTheContentView() {
        verify(splashActivity).setContentView(R.layout.activity_splash)
    }

    @Test
    fun onCreateRequestsTheConferenceData() {
        verify(presenter).requestConferenceData()
    }

    @Test
    fun onResumeOpensThePresenter() {
        subject.onResume(splashActivity)

        verify(presenter).open()
    }

    @Test
    fun onPauseClosesThePresenter() {
        subject.onPause(splashActivity)

        verify(presenter).close()
    }

    @Test
    fun itCanNavigateToTheMainActivity() {
        val intent = mock(Intent::class.java)

        `when`(intentFactory.make(splashActivity, MainActivity::class.java)).thenReturn(intent)

        subject.navigateToMain()

        verify(intentFactory).make(splashActivity, MainActivity::class.java)
        verify(splashActivity).startActivity(intent)
    }
}
