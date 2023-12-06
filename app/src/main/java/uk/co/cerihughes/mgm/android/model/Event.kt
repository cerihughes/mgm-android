package uk.co.cerihughes.mgm.android.model

import java.util.Date

class Event(
    val number: Int,
    val location: Location?,
    val date: Date?,
    val playlist: Playlist?,
    val classicAlbum: Album?,
    val newAlbum: Album?
)
