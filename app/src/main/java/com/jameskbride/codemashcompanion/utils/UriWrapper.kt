package com.jameskbride.codemashcompanion.utils

import android.net.Uri

class UriWrapper {

    fun parse(uri:String?): Uri {
        return Uri.parse(uri)
    }
}