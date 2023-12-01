package uk.co.cerihughes.mgm.android.repository.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uk.co.cerihughes.mgm.android.datasource.remote.generated.api.DefaultApi
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel

class RemoteDataSourceImpl : RemoteDataSource {

    private val api = DefaultApi()

    override fun getRemoteData(callback: RemoteDataSource.GetRemoteDataCallback<List<EventApiModel>>) {
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val events = api.events().toList()
                launch(Dispatchers.Main) {
                    callback.onDataLoaded(events)
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    callback.onDataNotAvailable()
                }
            }
        }
    }
}
