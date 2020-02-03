package io.arct.robotlib.extensions

import kotlin.math.round

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier).toInt() / multiplier
}

fun Double.map(from: ClosedRange<Double>, to: ClosedRange<Double>): Double =
    if (to.start <= to.endInclusive) (this - from.start) * (to.endInclusive - to.start) / (from.endInclusive - from.start) + to.start
    else (to.endInclusive + to.start) - this.map(from, (to.endInclusive)..(to.start))
