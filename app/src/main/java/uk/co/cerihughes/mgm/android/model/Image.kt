package uk.co.cerihughes.mgm.android.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Image(
    @PrimaryKey open var url: String = "",
    open var size: Int? = null
) : RealmObject()