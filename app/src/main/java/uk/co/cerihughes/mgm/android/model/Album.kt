package uk.co.cerihughes.mgm.android.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Album(
    @PrimaryKey open var id: String = "",
    open var isClassic: Boolean = false,
    open var spotifyId: String? = null,
    open var name: String = "",
    open var artist: String = "",
    open var score: Float? = null,
    open var images: RealmList<Image> = RealmList()
) : RealmObject()
