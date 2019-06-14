package uk.co.cerihughes.mgm.android.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uk.co.cerihughes.mgm.android.model.Event

class RepositoryFake : Repository {
    val getEventsLiveData = MutableLiveData<List<Event>>(emptyList())

    override fun getEvents(callback: Repository.GetOperationCallback<LiveData<List<Event>>>) {
        callback.onLocalData(getEventsLiveData)
        callback.onRemoteLoad()
    }
}