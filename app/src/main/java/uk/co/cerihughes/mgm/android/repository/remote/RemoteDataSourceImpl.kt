package uk.co.cerihughes.mgm.android.repository.remote

import uk.co.cerihughes.mgm.android.datasource.remote.generated.api.DefaultApi
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel

class RemoteDataSourceImpl : RemoteDataSource {

    private val api = DefaultApi()

    override fun getRemoteData(callback: RemoteDataSource.GetRemoteDataCallback<List<EventApiModel>>) {
        try {
            val events = api.events().toList()
            callback.onDataLoaded(events)
        } catch (e: Exception) {
            callback.onDataNotAvailable()
        }
    }
}