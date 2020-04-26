package uk.co.cerihughes.mgm.android.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Location(
    @PrimaryKey open var name: String = "",
    open var latitude: Double = 0.0,
    open var longitude: Double = 0.0
) : RealmObject()
