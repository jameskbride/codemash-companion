package com.jameskbride.codemashcompanion.speakers.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class SpeakerDetailFragmentImplTest {

    private lateinit var layoutInflater:LayoutInflater
    private lateinit var viewGroup:ViewGroup
    private lateinit var view:View
    private lateinit var qtn:SpeakerDetailFragment

    private lateinit var subject:SpeakerDetailFragmentImpl

    @Before
    fun setUp() {
        layoutInflater = mock()
        viewGroup = mock()
        view = mock()
        qtn = mock()
        subject = SpeakerDetailFragmentImpl()
    }

    @Test
    fun onCreateInflatesTheView() {
        whenever(layoutInflater.inflate(R.layout.fragment_speaker_detail, viewGroup, false)).thenReturn(view)

        val result = subject.onCreate(layoutInflater, viewGroup, null, qtn)

        verify(layoutInflater).inflate(R.layout.fragment_speaker_detail, viewGroup, false)
        assertSame(view, result)
    }
}