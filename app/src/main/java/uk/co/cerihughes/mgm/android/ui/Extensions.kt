package uk.co.cerihughes.mgm.android.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

fun PackageManager.isSpotifyInstalled(): Boolean {
    return try {
        getPackageInfo("com.spotify.music", 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Intent.launchSpotify(context: Context, spotifyURL: String) {
    data = Uri.parse(spotifyURL)
    putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://" + context.packageName))
    context.startActivity(this)
}
