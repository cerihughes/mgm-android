package uk.co.cerihughes.mgm.android.repository.remote

import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel

interface RemoteDataSource {

    interface GetRemoteDataCallback<T> {
        fun onDataLoaded(data: T)
    }

    fun getRemoteData(callback: GetRemoteDataCallback<Array<EventApiModel>>)
}