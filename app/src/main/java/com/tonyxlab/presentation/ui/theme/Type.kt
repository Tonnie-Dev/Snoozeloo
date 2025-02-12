package com.tonyxlab.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tonyxlab.R

val MontserratFontFamily = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal)

)
val Typography = Typography(
        displayLarge = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 57.sp,
                lineHeight = 64.sp,
                letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 45.sp,
                lineHeight = 52.sp,
                letterSpacing = 0.sp,
        ),
        displaySmall = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp,
                lineHeight = 44.sp,
                letterSpacing = 0.sp,
        ),
        headlineLarge = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.sp,
        ),
        headlineSmall = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp,
        ),
        titleLarge = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        titleSmall = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
        ),
        bodyLarge = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp,
        ),
        labelLarge = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
        ),
)
