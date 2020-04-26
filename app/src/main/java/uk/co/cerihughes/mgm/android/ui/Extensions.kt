package uk.co.cerihughes.mgm.android.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun PackageManager.isSpotifyInstalled(): Boolean {
    try {
        getPackageInfo("com.spotify.music", 0)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
}

fun Intent.launchSpotify(context: Context, spotifyURL: String) {
    data = Uri.parse(spotifyURL)
    putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://" + context.packageName))
    context.startActivity(this)
}
