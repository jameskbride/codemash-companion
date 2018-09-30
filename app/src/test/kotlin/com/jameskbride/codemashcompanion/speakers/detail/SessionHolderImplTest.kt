package com.jameskbride.codemashcompanion.speakers.detail

import android.content.Context
import android.view.View
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionHolderImplTest {

    @Mock private lateinit var qtn:SessionHolder
    @Mock private lateinit var layoutInflaterFactory: LayoutInflaterFactory
    @Mock private lateinit var view: View
    @Mock private lateinit var context: Context
    @Mock private lateinit var sessionTitle: TextView

    private lateinit var subject:SessionHolderImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = SessionHolderImpl()
    }

    @Test
    fun itInflatesTheView() {
        val session = FullSession(Title = "title")

        setupSessionHolderExpectations()

        subject.onInflate(session, context, qtn, layoutInflaterFactory = layoutInflaterFactory)

        verify(sessionTitle).setText(session.Title)
    }

    private fun setupSessionHolderExpectations() {
        whenever(layoutInflaterFactory.inflate(context, R.layout.view_session_holder, qtn, true)).thenReturn(view)
        whenever(view.findViewById<TextView>(R.id.session_title)).thenReturn(sessionTitle)
    }
}