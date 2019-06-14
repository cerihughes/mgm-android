package uk.co.cerihughes.mgm.android.repository

import androidx.lifecycle.LiveData
import uk.co.cerihughes.mgm.android.model.Event

interface Repository {

    interface GetOperationCallback<T> {
        fun onLocalData(data: T)
        fun onRemoteLoad()
    }

    fun getEvents(callback: GetOperationCallback<LiveData<List<Event>>>)
}