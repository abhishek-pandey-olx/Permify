package com.olx.permify.callback

interface ForwardToSettingsCallback {
    fun onPermanentPermissionDenied(permanentPermissionDenied: List<String>)
}
