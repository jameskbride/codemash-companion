package com.jameskbride.codemashcompanion.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.error.ErrorDialog
import com.jameskbride.codemashcompanion.error.ErrorDialogFactory
import com.jameskbride.codemashcompanion.error.ErrorDialogParams
import com.jameskbride.codemashcompanion.error.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.main.MainActivity
import com.jameskbride.codemashcompanion.utils.BundleFactory
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SplashActivityImplTest {

    @Mock private lateinit var splashActivity: SplashActivity
    @Mock private lateinit var presenter: SplashActivityPresenter
    @Mock private lateinit var intentFactory: IntentFactory
    @Mock private lateinit var bundle:Bundle
    @Mock private lateinit var bundleFactory: BundleFactory
    @Mock private lateinit var errorDialogFactory: ErrorDialogFactory
    @Mock private lateinit var errorDialog:ErrorDialog
    @Mock private lateinit var fragmentManager: androidx.fragment.app.FragmentManager

    
    private lateinit var subject: SplashActivityImpl

    @Before
    fun setUp() {
        initMocks(this)

        whenever(splashActivity.supportFragmentManager).thenReturn(fragmentManager)

        subject = SplashActivityImpl(presenter, intentFactory, errorDialogFactory, bundleFactory)

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
        val intent = mock<Intent>()

        whenever(intentFactory.make(splashActivity, MainActivity::class.java)).thenReturn(intent)

        subject.navigateToMain()

        verify(intentFactory).make(splashActivity, MainActivity::class.java)
        verify(splashActivity).startActivity(intent)
    }
    
    @Test
    fun itCanShowTheErrorDialog() {
        whenever(errorDialogFactory.make()).thenReturn(errorDialog)
        whenever(bundleFactory.make()).thenReturn(bundle)

        subject.showErrorDialog()

        val errorDialogParamsCaptor = argumentCaptor<ErrorDialogParams>()
        verify(bundle).putSerializable(eq(PARAMETER_BLOCK), errorDialogParamsCaptor.capture())
        verify(errorDialog).setArguments(bundle)
        verify(errorDialog).show(eq(fragmentManager), any())
        verify(errorDialog).setCancelable(false)

        assertEquals(R.string.oops, errorDialogParamsCaptor.firstValue.title)
        assertEquals(R.string.no_data_message, errorDialogParamsCaptor.firstValue.message)
    }
}
