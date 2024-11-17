package com.tonyxlab.utils

import androidx.compose.ui.text.substring


fun String.removeAmPmSuffix(): String {

    return if (this.length < 2) this else this.substring(0, this.length - 2)

}

fun String.getAmPmSuffix(): String {

    val startIndex = this.length - 2
    return substring(startIndex = startIndex)
}