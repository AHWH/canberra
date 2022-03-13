package com.sg.slightlyred.canberra.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

class CommonUtil {
    companion object {
        @JvmStatic
        fun convertDrawableToBitmap(source: Drawable?): Bitmap? {
            if (source == null) {
                return null
            }
            return if (source is BitmapDrawable) {
                source.bitmap
            } else {
                // copying drawable object to not manipulate on the same reference
                val constantState = source.constantState ?: return null
                val drawable = constantState.newDrawable().mutate()
                val bitmap: Bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth, drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                return bitmap
            }
        }
    }
}