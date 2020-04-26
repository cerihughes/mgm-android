package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.model.Event

interface Repository {

    interface GetOperationCallback<T> {
        fun onDataLoaded(data: T)
    }

    fun getEvents(callback: GetOperationCallback<List<Event>>)
}
