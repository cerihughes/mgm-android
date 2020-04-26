package uk.co.cerihughes.mgm.android.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Playlist(
    @PrimaryKey open var spotifyId: String = "",
    open var name: String = "",
    open var owner: String = "",
    open var images: RealmList<Image> = RealmList()
) : RealmObject()
