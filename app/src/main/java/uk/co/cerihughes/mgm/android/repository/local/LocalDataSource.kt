package uk.co.cerihughes.mgm.android.repository.local

import uk.co.cerihughes.mgm.android.model.Event

interface LocalDataSource {
    fun getEvents(): List<Event>
    fun addEvents(events: Collection<Event>)
}
