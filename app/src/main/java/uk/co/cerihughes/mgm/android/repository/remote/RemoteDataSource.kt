package uk.co.cerihughes.mgm.android.repository.remote

import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel

interface RemoteDataSource {

    interface GetRemoteDataCallback<T> {
        fun onDataLoaded(data: T)
        fun onDataNotAvailable()
    }

    fun getRemoteData(callback: GetRemoteDataCallback<List<EventApiModel>>)
}
