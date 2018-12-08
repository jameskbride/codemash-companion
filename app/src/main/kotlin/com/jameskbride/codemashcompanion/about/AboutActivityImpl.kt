package com.jameskbride.codemashcompanion.about

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.jameskbride.codemashcompanion.BuildConfig
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.framework.BaseActivity
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.UriWrapper

class AboutActivityImpl constructor(val intentFactory: IntentFactory = IntentFactory(), val uriWrapper: UriWrapper = UriWrapper())
    :BaseActivityImpl() {
    override fun onCreate(savedInstanceState: Bundle?, qtn: BaseActivity) {
        qtn.setContentView(R.layout.activity_about)
        setTitle(qtn, R.string.action_about)

        qtn.findViewById<TextView>(R.id.version_number).text = BuildConfig.VERSION_NAME

        configureActionBar(qtn)
        makeNavigableLink(qtn.resources.getString(R.string.codemash_companion_url), qtn as AboutActivity, R.id.github_block)
    }

    private fun makeNavigableLink(link: String?, activity: AboutActivity, blockId:Int) {
        val block = activity.findViewById<LinearLayout>(blockId)
        block.setOnClickListener(LinkOnClickListener(link, intentFactory, activity, uriWrapper))
    }

    override fun onResume(sessionDetailActivity: BaseActivity) {
    }

    override fun onPause(sessionDetailActivity: BaseActivity) {
    }
}

class LinkOnClickListener constructor(val url:String?, val intentFactory: IntentFactory, val activity: AboutActivity, val uriWrapper: UriWrapper): View.OnClickListener {
    override fun onClick(view:View?) {
        val intent = intentFactory.make(Intent.ACTION_VIEW)
        intent.data = uriWrapper.parse(url)
        activity.startActivity(intent)
    }
}