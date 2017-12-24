package com.jameskbride.codemashcompanion.about

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.UriWrapper

class AboutActivityImpl constructor(val intentFactory: IntentFactory = IntentFactory(), val uriWrapper: UriWrapper = UriWrapper()) {
    fun onCreate(savedInstanceState: Bundle?, qtn: AboutActivity) {
        qtn.setContentView(R.layout.activity_about)

        configureActionBar(qtn)
        makeNavigableLink(qtn.resources.getString(R.string.codemash_companion_url), qtn, R.id.github_block)
    }

    private fun configureActionBar(qtn: AboutActivity) {
        qtn.setSupportActionBar(qtn.findViewById(R.id.toolbar))
        qtn.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun makeNavigableLink(link: String?, activity: AboutActivity, blockId:Int) {
        val block = activity.findViewById<LinearLayout>(blockId)
        block.setOnClickListener(LinkOnClickListener(link, intentFactory, activity, uriWrapper))
    }

    fun onOptionsItemSelected(item: MenuItem?, qtn: AboutActivity): Boolean {
        when(item?.itemId) {
            android.R.id.home ->  {
                qtn.onBackPressed()
                return true
            }
            else -> return qtn.callSuperOnOptionsItemSelected(item!!)
        }
    }
}

class LinkOnClickListener constructor(val url:String?, val intentFactory: IntentFactory, val activity: AboutActivity, val uriWrapper: UriWrapper): View.OnClickListener {
    override fun onClick(view:View?) {
        val intent = intentFactory.make(Intent.ACTION_VIEW)
        intent.data = uriWrapper.parse(url)
        activity.startActivity(intent)
    }
}