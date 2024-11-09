package com.tonyxlab.utils

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen

fun SplashScreen.zoomSplashAnimation(key: Boolean) {

    setOnExitAnimationListener { splashScreenView ->

        val zoomX = ObjectAnimator.ofFloat(
                splashScreenView.iconView,
                View.SCALE_X,
                1f,
                0.2f
        )
                .apply {

                    interpolator = OvershootInterpolator()
                    duration = 7_00
                    doOnEnd { splashScreenView.remove() }
                }


        val zoomY = ObjectAnimator.ofFloat(
                splashScreenView.iconView,
                View.SCALE_Y,
                1f,
                .5f
        )
                .apply {

                    interpolator = OvershootInterpolator()
                    duration = 7_00
                    doOnEnd { splashScreenView.remove() }
                }


        zoomX.start()
        zoomY.start()
    }
}