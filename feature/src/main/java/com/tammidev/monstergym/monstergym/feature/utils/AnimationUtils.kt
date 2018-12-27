package com.tammidev.monstergym.monstergym.feature.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator

const val ONE_SECOND = 1000L
const val HALF_SECOND = 500L
const val QUARTER_SECOND = 250L

const val FREQ = 3f
const val DECAY = 2f

open class AnimationUtils {

    companion object {
        // interpolator that goes 1 -> -1 -> 1 -> -1 in a sine wave pattern.
        val DECAYING_SINE_WAVE = TimeInterpolator { input ->
            val raw = Math.sin(FREQ.toDouble() * input.toDouble() * 2.0 * Math.PI)
            (raw * Math.exp((-input * DECAY).toDouble())).toFloat()
        }

        val BLINK_INTERPOLATOR = TimeInterpolator { input ->
            val x: Int = (input * 10f).toInt()
            when (x) {
                in 0..3 -> 1f
                in 3..5 -> 0f
                in 5..7 -> 1f
                in 7..9 -> 0f
                in 9..10 -> 1f
                else -> 0f
            }
        }
    }
}

fun View.shake(shake: Float = -100f, duration: Long = HALF_SECOND): ViewPropertyAnimator {
    return animate()
            .xBy(shake)
            .setInterpolator(AnimationUtils.DECAYING_SINE_WAVE)
            .setDuration(duration)
}

fun View.fadeIn(startAlpha: Float = 0f, endAlpha: Float = 1f, duration: Long = ONE_SECOND): ViewPropertyAnimator {
    visibility = View.VISIBLE
    alpha = startAlpha
    return animate()
            .alpha(endAlpha)
            .setDuration(duration)
            .setInterpolator(AccelerateInterpolator())
}

fun View.fadeOut(startAlpha: Float = 1f, endAlpha: Float = 0f, duration: Long = ONE_SECOND): ViewPropertyAnimator {
    visibility = View.VISIBLE
    alpha = startAlpha
    return animate()
            .alpha(endAlpha)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
}

fun View.blink(duration: Long = HALF_SECOND): ViewPropertyAnimator {
    visibility = View.VISIBLE
    alpha = 1f
    return animate()
            .alphaBy(-1f)
            .setDuration(duration)
            .setInterpolator(AnimationUtils.BLINK_INTERPOLATOR)
}

fun View.expandUp(duration: Long = ONE_SECOND): ViewPropertyAnimator {
    visibility = View.VISIBLE
    y = height.toFloat()
    return animate()
            .y(-height.toFloat() / 2)
            .setDuration(duration)
            .setInterpolator(AccelerateInterpolator())
}

fun animationEndListener(onAnimationEnd: () -> Unit): Animator.AnimatorListener {
    return object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd()
        }
    }
}

