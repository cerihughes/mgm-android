package uk.co.cerihughes.mgm.android.repository.remote

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import uk.co.cerihughes.mgm.android.datasource.remote.generated.api.DefaultApi
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel

class RemoteDataSourceImpl : RemoteDataSource {

    private val api = DefaultApi()

    override fun getRemoteData(callback: RemoteDataSource.GetRemoteDataCallback<List<EventApiModel>>) {
        doAsync {
            try {
                val events = api.events().toList()
                uiThread {
                    callback.onDataLoaded(events)
                }
            } catch (e: Exception) {
                uiThread {
                    callback.onDataNotAvailable()
                }
            }
        }
    }
}