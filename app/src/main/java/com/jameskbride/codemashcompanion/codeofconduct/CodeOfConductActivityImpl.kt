package com.jameskbride.codemashcompanion.codeofconduct

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.framework.BaseActivity
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl

class CodeOfConductActivityImpl: BaseActivityImpl() {
    override fun onCreate(savedInstanceState: Bundle?, qtn: BaseActivity) {
        qtn.setContentView(R.layout.activity_code_of_conduct)

        val webView = qtn.findViewById<WebView>(R.id.code_of_conduct_webview)
        val webSettings = webView.settings
        webSettings.builtInZoomControls = true
        webView.webViewClient = CodeOfConductWebViewClient()
        webView.loadUrl(qtn.resources.getString(R.string.code_of_conduct_url))
    }

    override fun onResume(sessionDetailActivity: BaseActivity) {
    }

    override fun onPause(sessionDetailActivity: BaseActivity) {
    }
}

class CodeOfConductWebViewClient: WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }
}