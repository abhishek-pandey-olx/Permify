package com.olx.permify.callback

interface RationalPermissionCallback {
    fun onTemporaryPermissionDenied(temporaryPermissionDenied: List<String>)
}
