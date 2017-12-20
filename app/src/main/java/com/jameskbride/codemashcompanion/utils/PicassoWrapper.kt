package com.jameskbride.codemashcompanion.utils

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation

class PicassoWrapper {

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

    fun placeholder(placeholderId: Int): PicassoWrapper {
        this.requestCreator = this.requestCreator?.placeholder(placeholderId)
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

    fun transform(transformation: Transformation): PicassoWrapper {
        this.requestCreator = this.requestCreator?.transform(transformation)
        return this
    }

    fun into(imageView: ImageView?): RequestCreator? {
        this.requestCreator?.into(imageView)
        return requestCreator
    }
}