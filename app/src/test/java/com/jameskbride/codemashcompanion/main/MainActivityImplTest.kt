package com.jameskbride.codemashcompanion.main

import com.jameskbride.codemashcompanion.R
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MainActivityImplTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var presenter: MainActivityPresenter
    private lateinit var subject: MainActivityImpl

    @Before
    fun setUp() {
        mainActivity = mock(MainActivity::class.java)
        presenter = mock(MainActivityPresenter::class.java)
        subject = MainActivityImpl(presenter)
    }

    @Test
    fun itCanDelegateToTheMainActivity() {
        subject.onCreate(null, mainActivity)

        verify(mainActivity).setContentView(R.layout.activity_main)
    }

    @Test
    fun itOpensThePresenterOnResume() {
        subject.onResume(mainActivity)

        verify(presenter).open()
    }

    @Test
    fun itClosesThePresenterOnPause() {
        subject.onPause(mainActivity)

        verify(presenter).close()
    }
}
