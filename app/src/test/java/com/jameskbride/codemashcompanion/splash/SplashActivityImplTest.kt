package com.jameskbride.codemashcompanion.splash

import android.content.Intent
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.main.MainActivity
import com.jameskbride.codemashcompanion.utils.IntentFactory
import io.mockk.Runs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SplashActivityImplTest {

    private lateinit var splashActivity: SplashActivity
    private lateinit var presenter: SplashActivityPresenter
    private lateinit var subject: SplashActivityImpl
    private lateinit var intentFactory: IntentFactory

    @Before
    fun setUp() {
        splashActivity = mockk()
        presenter = mockk()
        intentFactory = mockk()
        subject = SplashActivityImpl(presenter, intentFactory)

        every {splashActivity.setContentView(any<Int>())} just Runs
        every {presenter.view = subject} just Runs
        every {presenter.open()} just Runs
        every {presenter.requestConferenceData()} just Runs
        subject.onCreate(null, splashActivity)
    }

    @Test
    fun onCreateSetsTheContentView() {
        verify{splashActivity.setContentView(R.layout.activity_splash)}
    }

    @Test
    fun onCreateRequestsTheConferenceData() {
        verify{presenter.requestConferenceData()}
    }

    @Test
    fun onResumeOpensThePresenter() {
        subject.onResume(splashActivity)

        verify{presenter.open()}
    }

    @Test
    fun onPauseClosesThePresenter() {
        every { presenter.close() } just Runs
        subject.onPause(splashActivity)

        verify{presenter.close()}
    }

    @Test
    fun itCanNavigateToTheMainActivity() {
        val intent = mockk<Intent>()

        every{intentFactory.make(splashActivity, MainActivity::class.java)} returns intent
        every{splashActivity.startActivity(intent)} just Runs

        subject.navigateToMain()

        verify{intentFactory.make(splashActivity, MainActivity::class.java)}
        verify{splashActivity.startActivity(intent)}
    }
}
