package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.model.Event

class RepositoryFake : Repository {
    val getEventsData: List<Event> = emptyList()

    override fun getEvents(callback: Repository.GetOperationCallback<List<Event>>) {
        callback.onDataLoaded(getEventsData)
    }
}
