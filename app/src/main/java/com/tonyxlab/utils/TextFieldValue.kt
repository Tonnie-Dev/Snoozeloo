package com.tonyxlab.utils


data class TextFieldValue<T>(
    val value: T,
    val isError: Boolean = false,
    val onValueChange: (() -> Unit)? = null
)
