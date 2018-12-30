package com.jameskbride.codemashcompanion.sessions.list.listitems

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.list.TimeViewHolder
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import java.util.*

class TimeHeaderListItem constructor(val sessionTime: Date): Item<TimeViewHolder>(), ExpandableItem {
    private lateinit var onToggleListener: ExpandableGroup

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.onToggleListener = onToggleListener
    }

    override fun bind(viewHolder: TimeViewHolder, position: Int) {
        viewHolder.bind(sessionTime)
    }

    override fun getLayout(): Int {
        return R.layout.sessions_time_header
    }

    override fun createViewHolder(itemView: View): TimeViewHolder {
        return TimeViewHolder(itemView)
    }
}