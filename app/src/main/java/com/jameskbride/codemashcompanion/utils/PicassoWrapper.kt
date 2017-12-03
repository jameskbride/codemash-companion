package com.jameskbride.codemashcompanion.utils

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class PicassoWrapper() {

    private var picassoBuilder: Picasso? = null
    private var requestCreator: RequestCreator? = null

    fun with(context: Context?): PicassoWrapper {
        this.picassoBuilder = Picasso.with(context)
        return this
    }

    fun load(url:String?): PicassoWrapper {
        this.requestCreator = this.picassoBuilder?.load(url)
        return this
    }

    fun into(imageView: ImageView?): RequestCreator? {
        this.requestCreator?.into(imageView)
        return requestCreator
    }
}