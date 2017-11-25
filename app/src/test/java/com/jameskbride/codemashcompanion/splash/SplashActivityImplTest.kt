package com.jameskbride.codemashcompanion.splash

import com.jameskbride.codemashcompanion.R
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SplashActivityImplTest {

    private lateinit var splashActivity: SplashActivity
    private lateinit var presenter: SplashActivityPresenter
    private lateinit var subject: SplashActivityImpl

    @Before
    fun setUp() {
        splashActivity = mock(SplashActivity::class.java)
        presenter = mock(SplashActivityPresenter::class.java)
        subject = SplashActivityImpl(presenter)
    }

    @Test
    fun onCreateSetsTheContentView() {
        subject.onCreate(null, splashActivity)

        verify(splashActivity).setContentView(R.layout.activity_splash)
    }

    @Test
    fun onCreateRequestsTheConferenceData() {
        subject.onCreate(null, splashActivity)

        verify(presenter).requestConferenceData()
    }
}
