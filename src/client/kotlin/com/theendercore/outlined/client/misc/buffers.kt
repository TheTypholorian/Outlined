package com.theendercore.outlined.client.misc

@JvmField
internal var COBS: CustomOutlineBufferSource? = null


fun getCustomOutlineBuffer(): CustomOutlineBufferSource {
    if (COBS == null) error("Trying to access CustomOutlineBufferSource before init!")
    return COBS!!
}