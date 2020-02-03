package io.arct.robotlib.navigation

import kotlin.math.atan2

object Angles {
    fun normalizeAngle(a: Double): Double {
        val r = a % 360

        return if (r.isNaN()) r else if (r > 180) r - 360 else if (r <= -180) r + 360 else r
    }

    fun generalAngle(a: Double): Double {
        val r = normalizeAngle(a)

        return if (r.isNaN()) r else if (r < 0) 360 + r else r
    }

    fun fromCoordinates(x: Double, y: Double): Double =
        if (-x != 0.0 || y != 0.0) normalizeAngle(atan2(y, -x) * (180 / Math.PI) - 90)
        else Double.NaN
}
