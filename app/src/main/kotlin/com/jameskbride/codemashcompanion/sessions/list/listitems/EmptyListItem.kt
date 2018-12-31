package com.jameskbride.codemashcompanion.sessions.list.listitems

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.list.EmptyViewHolder
import com.xwray.groupie.Item

class EmptyListItem: Item<EmptyViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.no_data
    }

    override fun bind(viewHolder: EmptyViewHolder, position: Int) {
        viewHolder.bind()
    }

    override fun createViewHolder(itemView: View): EmptyViewHolder {
        return EmptyViewHolder(itemView)
    }
}