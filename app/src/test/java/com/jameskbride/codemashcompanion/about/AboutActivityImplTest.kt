package com.jameskbride.codemashcompanion.about

import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class AboutActivityImplTest {

    @Mock private lateinit var aboutActivity:AboutActivity

    private lateinit var subject:AboutActivityImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = AboutActivityImpl()
    }

    @Test
    fun onCreateSetsTheContentView() {
        subject.onCreate(null, aboutActivity)

        verify(aboutActivity).setContentView(R.layout.activity_main)
    }
}