package io.arct.robotlib.exceptions

class InvalidDeviceException(device: String) :
    Exception("$device is not a valid Device!")
