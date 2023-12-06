package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.model.Event

class MockRepository : Repository {
    var getEventsResponse: List<Event> = emptyList()

    override suspend fun getEvents(): List<Event> {
        return getEventsResponse
    }
}
