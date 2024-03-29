package uk.co.cerihughes.mgm.android.ui.albumscores

import uk.co.cerihughes.mgm.android.model.Album
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.Repository
import uk.co.cerihughes.mgm.android.ui.RemoteDataLoadingViewModel
import java.util.Locale

class AlbumScoresViewModel(repository: Repository) : RemoteDataLoadingViewModel(repository) {

    private val comparator = compareByDescending<Album> { it.score }
        .thenBy { it.name.lowercase(Locale.getDefault()) }
        .thenBy { it.artist.lowercase(Locale.getDefault()) }

    private var allAlbums: List<Album> = emptyList()
    private var scoreViewModels: List<AlbumScoreViewModel> = emptyList()

    val isLoaded: Boolean get() = allAlbums.isNotEmpty()

    override fun setEvents(events: List<Event>) {
        allAlbums = events.map { mutableListOf(it.classicAlbum, it.newAlbum) }
            .flatten()
            .filterNotNull()
            .filter { it.score != null }
            .sortedWith(comparator)

        val positions = calculatePositions(allAlbums)
        scoreViewModels =
            allAlbums.mapIndexed { index, it -> AlbumScoreViewModel(it, positions[index]) }
    }

    fun numberOfScores(): Int {
        return scoreViewModels.size
    }

    fun scoreViewModel(index: Int): AlbumScoreViewModel? {
        return try {
            scoreViewModels[index]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    private fun calculatePositions(albums: List<Album>): List<String> {
        val scores = albums.map { it.score ?: 0.0f }
        val positions: MutableList<String> = mutableListOf()
        var currentPosition = 0
        var currentValue = 11.0f
        for ((index, value) in scores.iterator().withIndex()) {
            if (value != currentValue) {
                currentValue = value
                currentPosition = index + 1
            }
            positions.add("$currentPosition")
        }
        return positions
    }
}
