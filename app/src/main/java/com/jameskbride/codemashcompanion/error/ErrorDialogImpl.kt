package com.jameskbride.codemashcompanion.error

import android.support.annotation.StringRes
import java.io.Serializable
import javax.inject.Inject

class ErrorDialogImpl @Inject constructor() {
}

data class ErrorDialogParams constructor(@StringRes val title:Int, @StringRes val message:Int): Serializable

val PARAMETER_BLOCK = "PARAMETER_BLOCK"