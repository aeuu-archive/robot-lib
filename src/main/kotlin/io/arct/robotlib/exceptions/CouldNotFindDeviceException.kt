package io.arct.robotlib.exceptions

class CouldNotFindDeviceException(device: String?, identifier: String) :
    Exception("Could not find a ${device ?: "Device"} with identifier $identifier")
