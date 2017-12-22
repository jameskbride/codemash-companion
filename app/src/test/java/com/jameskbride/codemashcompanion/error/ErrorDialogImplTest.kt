package com.jameskbride.codemashcompanion.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.mock
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
    @Mock private lateinit var title:TextView
    @Mock private lateinit var message:TextView

    private lateinit var subject:ErrorDialogImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = ErrorDialogImpl()

        whenever(layoutInflater.inflate(R.layout.error_dialog, viewGroup, false)).thenReturn(view)
        whenever(view.findViewById<TextView>(R.id.error_dialog_title)).thenReturn(title)
        whenever(view.findViewById<TextView>(R.id.error_dialog_message)).thenReturn(message)
    }

    @Test
    fun onCreateViewItInflatesTheView() {
        setupErrorDialogParams()
        val result = subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        assertSame(view, result)
        verify(layoutInflater).inflate(R.layout.error_dialog, viewGroup, false)
    }

    @Test
    fun onCreateViewSetsTheTitleAndMessage() {
        val errorDialogParams = setupErrorDialogParams()

        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        verify(title).setText(errorDialogParams.title)
        verify(message).setText(errorDialogParams.message)
    }

    private fun setupErrorDialogParams(): ErrorDialogParams {
        val errorDialogParams = ErrorDialogParams(title = R.string.oops, message = R.string.no_data_message)
        val bundle = mock<Bundle>()
        whenever(qtn.arguments).thenReturn(bundle)
        whenever(bundle.getSerializable(PARAMETER_BLOCK)).thenReturn(errorDialogParams)
        return errorDialogParams
    }
}