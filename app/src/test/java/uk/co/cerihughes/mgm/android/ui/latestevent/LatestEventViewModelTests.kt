package uk.co.cerihughes.mgm.android.ui.latestevent

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.cerihughes.mgm.android.model.createEvent
import uk.co.cerihughes.mgm.android.repository.MockRepository

class LatestEventViewModelTests {
    lateinit var repository: MockRepository
    lateinit var viewModel: LatestEventViewModel

    @Before
    fun setUp() {
        repository = MockRepository()
        viewModel = LatestEventViewModel(repository)
    }

    @Test
    fun entitiesAndLocation() {
        val event = createEvent(1, 8.0f, 7.0f, locationName = "Location")
        viewModel.setEvents(listOf(event))

        Assert.assertEquals(5, viewModel.numberOfItems())
        Assert.assertEquals(2, viewModel.numberOfEntites())

        Assert.assertEquals("LOCATION", viewModel.headerTitle(0))
        Assert.assertEquals(Pair(0.0, 0.0), viewModel.mapReference())
        Assert.assertEquals("LISTENING TO", viewModel.headerTitle(2))
        Assert.assertNotNull(viewModel.eventEntityViewModel(3))
        Assert.assertNotNull(viewModel.eventEntityViewModel(4))
    }

    @Test
    fun entitiesNoLocation() {
        val event = createEvent(1, 8.0f, 7.0f)
        viewModel.setEvents(listOf(event))

        Assert.assertEquals(3, viewModel.numberOfItems())
        Assert.assertEquals(2, viewModel.numberOfEntites())

        Assert.assertEquals("LISTENING TO", viewModel.headerTitle(0))
        Assert.assertNotNull(viewModel.eventEntityViewModel(1))
        Assert.assertNotNull(viewModel.eventEntityViewModel(2))
    }
}