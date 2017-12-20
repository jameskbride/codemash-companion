package com.jameskbride.codemashcompanion.speakers.detail

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import com.jameskbride.codemashcompanion.data.model.FullSession

class SessionHolder: LinearLayout {
    val impl: SessionHolderImpl = SessionHolderImpl()

    @JvmOverloads
    constructor(
            session: FullSession,
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        impl.onInflate(session, context, this)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            session:FullSession,
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        impl.onInflate(session, context, this)
    }
}

class SessionHolderFactory {
    fun make(session: FullSession, context: Context):SessionHolder {
        return SessionHolder(session, context)
    }
}