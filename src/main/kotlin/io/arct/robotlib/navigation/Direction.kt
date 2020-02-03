package io.arct.robotlib.navigation

enum class Direction(val value: Double) {
    Forward(0.0),
    Backward(180.0),
    Left(90.0),
    Right(-90.0);
}