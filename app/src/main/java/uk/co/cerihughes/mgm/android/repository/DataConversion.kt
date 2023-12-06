package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.AlbumApiModel
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.ImageApiModel
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.LocationApiModel
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.PlaylistApiModel
import uk.co.cerihughes.mgm.android.model.Album
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.model.Image
import uk.co.cerihughes.mgm.android.model.Location
import uk.co.cerihughes.mgm.android.model.Playlist
import java.text.SimpleDateFormat
import java.util.Date

private val DATE_FORMAT = "dd/MM/yyyy"
private val FORMATTER = SimpleDateFormat(DATE_FORMAT)

fun String.toSystemDefaultDate(): Date? {
    return FORMATTER.parse(this)
}

fun EventApiModel.toDataModel(): Event {
    return Event(number,
        location?.toDataModel(),
        date?.toSystemDefaultDate(),
        playlist?.toDataModel(),
        classicAlbum?.toDataModel(number),
        newAlbum?.toDataModel(number)
    )
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
        safeImages().map { it.toDataModel() }
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
        safeImages().map { it.toDataModel() }
    )
}

fun ImageApiModel.toDataModel(): Image {
    return Image(url, propertySize)
}