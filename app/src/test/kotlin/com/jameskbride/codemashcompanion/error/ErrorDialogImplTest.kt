package com.jameskbride.codemashcompanion.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.argumentCaptor
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
    @Mock private lateinit var okButton:TextView
    @Mock private lateinit var presenter:ErrorDialogPresenter

    private lateinit var subject:ErrorDialogImpl

    private val errorDialogParams = ErrorDialogParams(title = R.string.oops, message = R.string.no_data_message)

    @Before
    fun setUp() {
        initMocks(this)

        subject = ErrorDialogImpl(presenter)

        whenever(layoutInflater.inflate(R.layout.error_dialog, viewGroup, true)).thenReturn(view)
        whenever(view.findViewById<TextView>(R.id.error_dialog_title)).thenReturn(title)
        whenever(view.findViewById<TextView>(R.id.error_dialog_message)).thenReturn(message)
        whenever(view.findViewById<TextView>(R.id.error_dialog_ok)).thenReturn(okButton)

        setupErrorDialogParams(errorDialogParams)
    }

    @Test
    fun onCreateViewItInflatesTheView() {
        val result = subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        assertSame(view, result)
        verify(layoutInflater).inflate(R.layout.error_dialog, viewGroup, true)
    }

    @Test
    fun onCreateViewSetsTheTitleAndMessage() {
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        verify(title).setText(errorDialogParams.title)
        verify(message).setText(errorDialogParams.message)
    }

    @Test
    fun onCreateViewConfiguresOKButtonToDismissTheDialog() {
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        val onClickListenerCaptor = argumentCaptor<View.OnClickListener>()
        verify(okButton).setOnClickListener(onClickListenerCaptor.capture())
        onClickListenerCaptor.firstValue.onClick(null)

        verify(qtn).dismiss()
        verify(presenter).navigateToMain()
    }

    private fun setupErrorDialogParams(errorDialogParams: ErrorDialogParams): ErrorDialogParams {
        val bundle = mock<Bundle>()
        whenever(qtn.arguments).thenReturn(bundle)
        whenever(bundle.getSerializable(PARAMETER_BLOCK)).thenReturn(errorDialogParams)
        return errorDialogParams
    }
}