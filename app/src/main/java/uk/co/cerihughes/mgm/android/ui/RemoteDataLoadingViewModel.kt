package uk.co.cerihughes.mgm.android.ui

import androidx.lifecycle.ViewModel
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.Repository

abstract class RemoteDataLoadingViewModel(private val repository: Repository) : ViewModel() {

    interface LoadDataCallback {
        fun onDataLoaded()
    }

    fun loadData(callback: LoadDataCallback) {
        repository.getEvents(object : Repository.GetOperationCallback<List<Event>> {
            override fun onDataLoaded(data: List<Event>) {
                setEvents(data)
                callback.onDataLoaded()
            }
        })
    }

    abstract fun setEvents(events: List<Event>)
}