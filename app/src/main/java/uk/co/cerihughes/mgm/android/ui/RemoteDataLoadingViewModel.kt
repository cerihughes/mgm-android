package uk.co.cerihughes.mgm.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.Repository

abstract class RemoteDataLoadingViewModel(private val repository: Repository) : ViewModel() {

    protected val mutableEvents = MutableLiveData<List<Event>>(ArrayList())

    interface LoadDataCallback {
        fun onDataLoaded()
    }

    fun loadData(callback: LoadDataCallback) {
        repository.getEvents(object : Repository.GetOperationCallback<LiveData<List<Event>>> {
            override fun onLocalData(data: LiveData<List<Event>>) {
                mutableEvents.postValue(data.value)
            }

            override fun onRemoteLoad() {
                callback.onDataLoaded()
            }
        })
    }
}