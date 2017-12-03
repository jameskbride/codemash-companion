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

    fun fit(): PicassoWrapper {
        this.requestCreator = this.requestCreator?.fit()
        return this
    }

    fun centerCrop(): PicassoWrapper {
        this.requestCreator = this.requestCreator?.centerCrop()
        return this
    }

    fun resize(width: Int, height:Int): PicassoWrapper {
        this.requestCreator = this.requestCreator?.resize(width, height)
        return this
    }

    fun into(imageView: ImageView?): RequestCreator? {
        this.requestCreator?.into(imageView)
        return requestCreator
    }
}