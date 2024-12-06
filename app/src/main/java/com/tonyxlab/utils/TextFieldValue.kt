package com.tonyxlab.utils


data class TextFieldValue<T>(
    val value: T,
    val isError: Boolean = false,
    val onValueChange: ((T) -> Unit)? ,
    val range: IntRange = 0..Int.MAX_VALUE,
    val maxCharacters: Int = Int.MAX_VALUE
)