package com.olx.permify

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.olx.permify.callback.ForwardToSettingsCallback
import com.olx.permify.callback.PermissionRequestCallback
import com.olx.permify.callback.RationalPermissionCallback
import com.olx.permify.utils.Logger
import com.olx.permify.utils.OPEN_SETTING_MESSAGE
import com.olx.permify.utils.REQUEST_MESSAGE
import java.lang.ref.WeakReference

object Permify {

    @JvmOverloads
    fun requestPermission(
        activity: FragmentActivity,
        permissions: List<String>,
        forwardToSettingsCallback: ForwardToSettingsCallback? = null,
        rationalPermissionCallback: RationalPermissionCallback? = null,
        permissionRequestCallback: PermissionRequestCallback? = null,
        requestMessage: String = REQUEST_MESSAGE,
        openSettingMessage: String = OPEN_SETTING_MESSAGE,
        showDialogs: Boolean = true,
        enableLogs: Boolean = false
    ) {
        Logger.debug = enableLogs
        val weakActivity = WeakReference(activity)
        val mutablePermissionList = permissions.toMutableList()
        PermissionRequestBuilder(weakActivity, null, mutablePermissionList)
            .displayPermissionDialogs(showDialogs)
            .setPermissionCallbacks(rationalPermissionCallback, forwardToSettingsCallback)
            .setPermissionRequestMessages(requestMessage, openSettingMessage)
            .buildAndRequest(permissionRequestCallback)
    }

    @JvmOverloads
    fun requestPermission(
        fragment: Fragment,
        permissions: List<String>,
        rationalPermissionCallback: RationalPermissionCallback? = null,
        forwardToSettingsCallback: ForwardToSettingsCallback? = null,
        permissionRequestCallback: PermissionRequestCallback? = null,
        requestMessage: String = REQUEST_MESSAGE,
        openSettingMessage: String = OPEN_SETTING_MESSAGE,
        showDialogs: Boolean = true,
        enableLogs: Boolean = false
    ) {
        Logger.debug = enableLogs
        val weakActivity = WeakReference(fragment.requireActivity())
        val weakFragment = WeakReference(fragment)
        val mutablePermissionList = permissions.toMutableList()
        return PermissionRequestBuilder(weakActivity, weakFragment, mutablePermissionList)
            .displayPermissionDialogs(showDialogs)
            .setPermissionCallbacks(rationalPermissionCallback, forwardToSettingsCallback)
            .setPermissionRequestMessages(requestMessage, openSettingMessage)
            .buildAndRequest(permissionRequestCallback)
    }

    fun isNotificationsEnabled(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isPermissionTemporarilyDenied(activity: FragmentActivity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    @JvmStatic
    fun isPermissionTemporarilyDenied(fragment: Fragment, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            fragment.requireActivity(),
            permission
        )
    }

}
