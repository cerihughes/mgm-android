package uk.co.cerihughes.mgm.android.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Event(
    @PrimaryKey open var number: Int = 0,
    open var location: Location? = null,
    open var date: Date? = null,
    open var playlist: Playlist? = null,
    open var classicAlbum: Album? = null,
    open var newAlbum: Album? = null
) : RealmObject()
