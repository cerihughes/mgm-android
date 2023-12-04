package uk.co.cerihughes.mgm.android.repository

import io.realm.RealmList
import io.realm.RealmModel
import java.text.SimpleDateFormat
import java.util.*
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.*
import uk.co.cerihughes.mgm.android.model.*

private val DATE_FORMAT = "dd/MM/yyyy"
private val FORMATTER = SimpleDateFormat(DATE_FORMAT)

fun String.toSystemDefaultDate(): Date? {
    return FORMATTER.parse(this)
}

fun EventApiModel.toDataModel(): Event {
    return Event(number,
        location?.let { apiModel -> apiModel.toDataModel() },
        date?.let { it.toSystemDefaultDate() },
        playlist?.let { it.toDataModel() },
        classicAlbum?.let { it.toDataModel(number) },
        newAlbum?.let { it.toDataModel(number) })
}

fun LocationApiModel.toDataModel(): Location {
    return Location(name, latitude, longitude)
}

fun AlbumApiModel.safeImages() = images ?: emptyList()

fun AlbumApiModel.toDataModel(eventNumber: Int): Album {
    return Album(
        type.createId(eventNumber),
        type.toDataModel(),
        spotifyId,
        name,
        artist,
        score,
        safeImages().map { it.toDataModel() }.asRealmList()
    )
}

fun AlbumApiModel.Type.toDataModel(): Boolean {
    return this == AlbumApiModel.Type.classic
}

fun AlbumApiModel.Type.createId(eventNumber: Int): String {
    return "$eventNumber-${this.value}"
}

fun PlaylistApiModel.safeImages() = images ?: emptyList()

fun PlaylistApiModel.toDataModel(): Playlist {
    return Playlist(
        spotifyId,
        name,
        owner,
        safeImages().map { it.toDataModel() }.asRealmList()
    )
}

fun ImageApiModel.toDataModel(): Image {
    return Image(url, propertySize)
}

fun <T : RealmModel> Collection<T>.asRealmList(): RealmList<T> {
    val realmList = RealmList<T>()
    for (item in this) {
        realmList.add(item)
    }
    return realmList
}
