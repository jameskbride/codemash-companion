package com.jameskbride.codemashcompanion.sessions.list.listitems

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.ItemViewHolder
import com.xwray.groupie.Item

class SessionListItem(val session: FullSession, val onClick: View.OnClickListener): Item<ItemViewHolder>() {
    override fun bind(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(session)
        viewHolder.view.setOnClickListener(onClick)
    }

    override fun getLayout(): Int {
        return R.layout.sessions_item
    }

    override fun createViewHolder(itemView: View): ItemViewHolder {
        return ItemViewHolder(itemView)
    }
}