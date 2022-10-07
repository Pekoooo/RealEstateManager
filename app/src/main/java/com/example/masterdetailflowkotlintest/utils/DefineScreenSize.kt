package com.example.masterdetailflowkotlintest.utils

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.Dimension
import kotlin.math.roundToInt

class DefineScreenSize {

    companion object {

        @Dimension(unit = Dimension.DP)
        fun pxToDp(windowManager: WindowManager, @Dimension(unit = Dimension.PX) px: Int): Int {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            return (px / displayMetrics.densityDpi.toFloat() * DisplayMetrics.DENSITY_DEFAULT).roundToInt()
        }

        fun isTablet(context: Context): Boolean {
            val outSize = Point()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getRealSize(outSize)
            outSize.x = pxToDp(windowManager, outSize.x)
            outSize.y = pxToDp(windowManager, outSize.y)
            val shorterSideDp: Int
            val longerSideDp: Int
            if (outSize.x > outSize.y) {
                shorterSideDp = outSize.y
                longerSideDp = outSize.x
            } else {
                shorterSideDp = outSize.x
                longerSideDp = outSize.y
            }
            return shorterSideDp > 480 && longerSideDp > 640
        }


    }
}