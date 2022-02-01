package com.sg.slightlyred.canberra.view.behaviour

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class AppBarCustomBehaviour(context: Context, attributeSet: AttributeSet) : AppBarLayout.ScrollingViewBehavior(context, attributeSet) {
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is FrameLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (parent == null || child == null || dependency == null) return false

        parent.getDependencies(child).forEach {
            if (it is ConstraintLayout) {
                Log.i("Test", it.y.toString() + it.hasOverlappingRendering)
            }
        }
        // Frame layout always reset its y position
        if (dependency.y <= 180) {
            child.setBackgroundColor(Color.BLACK)
        } else {
            child.setBackgroundColor(Color.TRANSPARENT)
        }
        return true
    }
}