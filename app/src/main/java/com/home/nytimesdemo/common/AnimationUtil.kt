package com.home.nytimesdemo.common

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

object AnimationUtil {

    fun fadeOutFadeIn(v: View, onFadeInStart: () -> Unit = {}) {
        val fadeOut = ObjectAnimator
            .ofFloat(v, View.ALPHA, 1f, 0f)
            .setDuration(200)
        val fadeIn = ObjectAnimator
            .ofFloat(v, View.ALPHA, 0f, 1f)
            .setDuration(200)
        fadeIn.addListener(object : AnimationCallback() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                onFadeInStart.invoke()
            }
        })
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(fadeOut, fadeIn)
        animatorSet.start()
    }

    fun collapse(view: View) {
        val initialHeight = view.measuredHeight
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        animation.duration = 500L
        view.startAnimation(animation)
    }

    fun expand(view: View) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = view.measuredHeight
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                view.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
                    else (targetHeight * interpolatedTime).toInt()
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        animation.duration = 500L
        view.startAnimation(animation)
    }

    private abstract class AnimationCallback : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {

        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationRepeat(animation: Animator?) {

        }
    }
}