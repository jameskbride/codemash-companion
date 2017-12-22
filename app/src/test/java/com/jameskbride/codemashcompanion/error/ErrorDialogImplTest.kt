package com.jameskbride.codemashcompanion.error

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class ErrorDialogImplTest {

    @Mock private lateinit var layoutInflater:LayoutInflater
    @Mock private lateinit var viewGroup:ViewGroup
    @Mock private lateinit var qtn:ErrorDialog
    @Mock private lateinit var view: View

    private lateinit var subject:ErrorDialogImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = ErrorDialogImpl()

        whenever(layoutInflater.inflate(R.layout.error_dialog, viewGroup, false)).thenReturn(view)
    }

    @Test
    fun onCreateViewItInflatesTheView() {
        val result = subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        assertSame(view, result)
        verify(layoutInflater).inflate(R.layout.error_dialog, viewGroup, false)
    }
}