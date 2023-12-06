package uk.co.cerihughes.mgm.android.model

class Album(
    val id: String,
    val isClassic: Boolean,
    val spotifyId: String?,
    val name: String,
    val artist: String,
    val score: Float?,
    val images: List<Image>
)
