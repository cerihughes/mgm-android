package uk.co.cerihughes.mgm.android.repository.local

import androidx.lifecycle.LiveData
import uk.co.cerihughes.mgm.android.model.Event

interface LocalDataSource {
    fun getEvents(): LiveData<List<Event>>
    fun addEvents(events: Collection<Event>)
}