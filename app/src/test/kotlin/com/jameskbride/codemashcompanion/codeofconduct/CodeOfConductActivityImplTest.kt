package com.jameskbride.codemashcompanion.codeofconduct

import android.content.res.Resources
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.framework.DefaultToolbar
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class CodeOfConductActivityImplTest {

    @Mock private lateinit var qtn:CodeOfConductActivity
    @Mock private lateinit var codeOfConductWebView:WebView
    @Mock private lateinit var webSettings:WebSettings
    @Mock private lateinit var resources:Resources
    @Mock private lateinit var defaultToolbar:DefaultToolbar

    private lateinit var subject:CodeOfConductActivityImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = CodeOfConductActivityImpl(defaultToolbar)

        whenever(qtn.findViewById<WebView>(R.id.code_of_conduct_webview)).thenReturn(codeOfConductWebView)
        whenever(codeOfConductWebView.settings).thenReturn(webSettings)
        whenever(qtn.resources).thenReturn(resources)
    }

    @Test
    fun onCreateInflatesTheView() {
        subject.onCreate(null, qtn)

        verify(qtn).setContentView(R.layout.activity_code_of_conduct)
    }

    @Test
    fun onCreateSetsTheCodeOfConductUrl() {
        val url = "http://www.codemash.org/codemash-code-conduct"
        whenever(resources.getString(R.string.code_of_conduct_url)).thenReturn(url)

        subject.onCreate(null, qtn)

        verify(codeOfConductWebView).loadUrl(url)

        val argumentCaptor = argumentCaptor<WebViewClient>()
        verify(codeOfConductWebView).setWebViewClient(argumentCaptor.capture())

        assertFalse(argumentCaptor.firstValue.shouldOverrideUrlLoading(null, ""))
    }

    @Test
    fun onCreateConfiguresTheToolbar() {
        subject.onCreate(null, qtn)

        verify(defaultToolbar).setTitle(qtn, R.string.code_of_conduct)
        verify(defaultToolbar).configureActionBar(qtn)
    }
}