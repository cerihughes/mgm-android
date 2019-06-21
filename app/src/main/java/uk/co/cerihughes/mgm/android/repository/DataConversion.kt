package uk.co.cerihughes.mgm.android.repository

import io.realm.RealmList
import io.realm.RealmModel
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.*
import uk.co.cerihughes.mgm.android.model.*
import java.util.*

fun LocalDate.toSystemDefaultDate(): Date {
    return DateTimeUtils.toDate(atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
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

fun AlbumApiModel.safeImages() = images ?: emptyArray()

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
    return "${eventNumber}-${this.value}"
}

fun PlaylistApiModel.safeImages() = images ?: emptyArray()

fun PlaylistApiModel.toDataModel(): Playlist {
    return Playlist(
        spotifyId,
        name,
        owner,
        safeImages().map { it.toDataModel() }.asRealmList()
    )
}

fun ImageApiModel.toDataModel(): Image {
    return Image(url, size)
}

fun <T : RealmModel> Collection<T>.asRealmList(): RealmList<T> {
    val realmList = RealmList<T>()
    for (item in this) {
        realmList.add(item)
    }
    return realmList
}
