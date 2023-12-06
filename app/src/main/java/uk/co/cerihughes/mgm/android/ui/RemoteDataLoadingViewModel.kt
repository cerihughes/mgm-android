package uk.co.cerihughes.mgm.android.ui

import androidx.lifecycle.ViewModel
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.Repository

abstract class RemoteDataLoadingViewModel(private val repository: Repository) : ViewModel() {

    suspend fun loadData() {
        setEvents(repository.getEvents())
    }

    abstract fun setEvents(events: List<Event>)
}
