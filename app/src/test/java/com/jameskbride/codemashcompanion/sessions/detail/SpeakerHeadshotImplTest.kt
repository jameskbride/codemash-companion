package com.jameskbride.codemashcompanion.sessions.detail

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.Speaker
import com.jameskbride.codemashcompanion.utils.CircleTransform
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.jameskbride.codemashcompanion.utils.PicassoLoader
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SpeakerHeadshotImplTest {

    @Mock private lateinit var qtn:SpeakerHeadshot
    @Mock private lateinit var layoutInflaterFactory:LayoutInflaterFactory
    @Mock private lateinit var picassoLoader:PicassoLoader
    @Mock private lateinit var view:View
    @Mock private lateinit var context:Context
    @Mock private lateinit var speakerHeadShot:ImageView

    private lateinit var subject:SpeakerHeadshotImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = SpeakerHeadshotImpl()
    }

    @Test
    fun itInflatesTheView() {
        val speaker = Speaker()

        whenever(layoutInflaterFactory.inflate(context, R.layout.view_speaker_headshot, qtn, true)).thenReturn(view)
        whenever(view.findViewById<ImageView>(R.id.speaker_headshot)).thenReturn(speakerHeadShot)

        subject.onInflate(speaker, context, qtn, layoutInflaterFactory = layoutInflaterFactory, picassoLoader = picassoLoader)

        verify(picassoLoader).load(eq(speaker), eq(speakerHeadShot), anyInt(), anyInt(), any<CircleTransform>())
    }
}