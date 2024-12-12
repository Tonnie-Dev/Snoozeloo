package com.tonyxlab.utils

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import com.tonyxlab.R




@Composable
fun getBlue_100() = colorResource(R.color.blue_100)

@Composable
fun getBlack_900() = colorResource(R.color.black_900)


fun getPrimaryColor(stringColor: String = "#4664FF"): Color {
    return Color(android.graphics.Color.parseColor(stringColor))
}

@Composable
fun Context.getPrimaryColor(): Int {
    return MaterialTheme.colorScheme.primary.toArgb()
}

