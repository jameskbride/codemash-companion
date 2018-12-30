package com.jameskbride.codemashcompanion.sessions.list.listitems

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.list.DateViewHolder
import com.xwray.groupie.Item

class DateHeaderListItem constructor(val text:String): Item<DateViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.sessions_date_header
    }

    override fun bind(viewHolder: DateViewHolder, position: Int) {
        viewHolder.bind(text)
    }

    override fun createViewHolder(itemView: View): DateViewHolder {
        return DateViewHolder(itemView)
    }
}