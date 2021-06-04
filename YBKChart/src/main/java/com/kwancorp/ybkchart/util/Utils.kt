package com.kwancorp.ybkchart.util

import android.graphics.RectF

class Utils {
    companion object {
        fun getCenterX(rectF: RectF): Float {
            return rectF.left + ((rectF.right - rectF.left) / 2F)
        }

        fun getCenterY(rectF: RectF): Float {
            return rectF.top + ((rectF.bottom - rectF.top) / 2F)
        }
    }
}