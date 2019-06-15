package uk.co.cerihughes.mgm.android.ui.albumscores

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.cerihughes.mgm.android.model.*
import uk.co.cerihughes.mgm.android.repository.RepositoryFake
import uk.co.cerihughes.mgm.android.ui.loadDataSync

class AlbumScoresViewModelTests {
    lateinit var repository: RepositoryFake
    lateinit var viewModel: AlbumScoresViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = RepositoryFake()
        viewModel = AlbumScoresViewModel(repository)
    }

    @Test
    fun testAlbums_scoreSort_differentScores() {
        val event1 = createEvent(1, 8.0f, 7.0f)
        val event2 = createEvent(2, 5.0f, 6.0f)
        val event3 = createEvent(3, 10.0f, 9.0f)
        repository.getEventsLiveData.value = listOf(event1, event2, event3)
        viewModel.loadDataSync()

        assert(
            positions = listOf("1", "2", "3", "4", "5", "6"),
            ratings = listOf("10.0", "9.0", "8.0", "7.0", "6.0", "5.0")
        )
    }

    @Test
    fun testAlbums_scoreSort_sameScores() {
        val event1 = createEvent(1, 10.0f, 9.5f)
        val event2 = createEvent(2, 10.0f, 9.5f)
        val event3 = createEvent(3, 10.0f, 9.5f)
        repository.getEventsLiveData.value = listOf(event1, event2, event3)
        viewModel.loadDataSync()

        assert(
            positions = listOf("1", "1", "1", "4", "4", "4"),
            ratings = listOf("10.0", "10.0", "10.0", "9.5", "9.5", "9.5")
        )
    }

    @Test
    fun testAlbums_scoreSort_mixedScores() {
        val event1 = createEvent(1, 4.4f, 5.5f)
        val event2 = createEvent(2, 5.5f, 4.4f)
        val event3 = createEvent(3, 3.3f, 5.5f)
        val event4 = createEvent(4, 6.6f, 6.6f)
        val event5 = createEvent(5, 6.6f, 6.6f)
        repository.getEventsLiveData.value = listOf(event1, event2, event3, event4, event5)
        viewModel.loadDataSync()

        assert(
            positions = listOf("1", "1", "1", "1", "5", "5", "5", "8", "8", "10"),
            ratings = listOf("6.6", "6.6", "6.6", "6.6", "5.5", "5.5", "5.5", "4.4", "4.4", "3.3")
        )
    }

    @Test
    fun testAlbums_nameSort() {
        val event1 = createEventByAlbumName(1, "AA", "dd")
        val event2 = createEventByAlbumName(2, "bb", "EE")
        val event3 = createEventByAlbumName(3, "CC", "ff")
        repository.getEventsLiveData.value = listOf(event1, event2, event3)
        viewModel.loadDataSync()

        assert(
            positions = listOf("1", "1", "1", "1", "1", "1"),
            albumNames = listOf("AA", "bb", "CC", "dd", "EE", "ff")
        )
    }

    @Test
    fun testAlbums_artistSort() {
        val event1 = createEventByAlbumArtist(1, "Aa", "ab")
        val event2 = createEventByAlbumArtist(2, "ACa1", "ACA2")
        val event3 = createEventByAlbumArtist(3, "aDEe3", "AdeE4")
        repository.getEventsLiveData.value = listOf(event1, event2, event3)
        viewModel.loadDataSync()

        assert(
            positions = listOf("1", "1", "1", "1", "1", "1"),
            artistNames = listOf("Aa", "ab", "ACa1", "ACA2", "aDEe3", "AdeE4")
        )
    }

    @Test
    fun testAlbums_allSorts() {
        val classicAlbum1 = createAlbum(AlbumType.CLASSIC, "zzz", "aaa", 8.0f)
        val classicAlbum2 = createAlbum(AlbumType.CLASSIC, "B2", "yyy", 9.0f)
        val classicAlbum3 = createAlbum(AlbumType.CLASSIC, "A0", "ZZZ", 10.0f)
        val classicAlbum4 = createAlbum(AlbumType.CLASSIC, "B2", "xxx", 9.0f)
        val classicAlbum5 = createAlbum(AlbumType.CLASSIC, "A1", "Z1", 10.0f)

        val newAlbum1 = createAlbum(AlbumType.NEW, "2", "Art", 7.0f)
        val newAlbum2 = createAlbum(AlbumType.NEW, "aaa", "zzz", 8.0f)
        val newAlbum3 = createAlbum(AlbumType.NEW, "1", "Art", 7.0f)
        val newAlbum4 = createAlbum(AlbumType.NEW, "B2", "zzz", 9.0f)
        val newAlbum5 = createAlbum(AlbumType.NEW, "A1", "A2", 10.0f)

        val event1 = createEvent(1, classicAlbum1, newAlbum1)
        val event2 = createEvent(2, classicAlbum2, newAlbum2)
        val event3 = createEvent(3, classicAlbum3, newAlbum3)
        val event4 = createEvent(4, classicAlbum4, newAlbum4)
        val event5 = createEvent(5, classicAlbum5, newAlbum5)

        repository.getEventsLiveData.value = listOf(event1, event2, event3, event4, event5)
        viewModel.loadDataSync()

        assert(
            positions = listOf("1", "1", "1", "4", "4", "4", "7", "7", "9", "9"),
            ratings = listOf("10.0", "10.0", "10.0", "9.0", "9.0", "9.0", "8.0", "8.0", "7.0", "7.0"),
            albumNames = listOf("A0", "A1", "A1", "B2", "B2", "B2", "aaa", "zzz", "1", "2"),
            artistNames = listOf("ZZZ", "A2", "Z1", "xxx", "yyy", "zzz", "zzz", "aaa", "Art", "Art")
        )
    }

    private fun assert(
        positions: List<String>,
        ratings: List<String>? = null,
        albumNames: List<String>? = null,
        artistNames: List<String>? = null
    ) {

        assertEquals(positions.size, viewModel.numberOfScores())
        for (index in 0 until viewModel.numberOfScores()) {
            val scoreViewModel = viewModel.scoreViewModel(index)!!
            assertEquals(scoreViewModel.position(), positions[index])
            ratings?.let {
                assertEquals(it[index], scoreViewModel.rating())
            }
            albumNames?.let {
                assertEquals(it[index], scoreViewModel.albumName())
            }
            artistNames?.let {
                assertEquals(it[index], scoreViewModel.artistName())
            }
        }
    }
}