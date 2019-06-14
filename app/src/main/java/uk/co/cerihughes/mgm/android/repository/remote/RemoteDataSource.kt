package uk.co.cerihughes.mgm.android.repository.remote

import uk.co.cerihughes.mgm.android.model.Event

interface RemoteDataSource {

    interface GetRemoteDataCallback<T> {
        fun onDataLoaded(data: T)
        fun onDataNotAvailable()
    }

    fun getRemoteData(callback: GetRemoteDataCallback<List<Event>>)
}