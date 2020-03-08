package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.model.Event

class MockRepository : Repository {
    var getEventsResponse: List<Event> = emptyList()

    override fun getEvents(callback: Repository.GetOperationCallback<List<Event>>) {
        callback.onDataLoaded(getEventsResponse)
    }
}