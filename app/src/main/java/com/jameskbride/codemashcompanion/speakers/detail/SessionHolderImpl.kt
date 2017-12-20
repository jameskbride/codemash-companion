package com.jameskbride.codemashcompanion.speakers.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory

class SessionHolderImpl {
    fun onInflate(session: FullSession, context: Context, qtn: SessionHolder, attrs: AttributeSet? = null,
                  defStyleAttr: Int = 0, layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory()) {
        val view = layoutInflaterFactory.inflate(context, R.layout.view_session_holder, qtn, true)
        view!!.findViewById<TextView>(R.id.session_title).text = session.Title
    }
}