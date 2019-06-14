package uk.co.cerihughes.mgm.android.repository.remote

import android.content.Context
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.datasource.remote.generated.api.DefaultApi
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel
import uk.co.cerihughes.mgm.android.repository.GsonFactory

class RemoteDataSourceImpl(context: Context) : RemoteDataSource {

    private val api = DefaultApi()
    private val fallbackData: String
    private val gson = GsonFactory.createGson()

    init {
        fallbackData = context.resources.openRawResource(R.raw.mgm).bufferedReader().use { it.readText() }
    }

    override fun getRemoteData(callback: RemoteDataSource.GetRemoteDataCallback<Array<EventApiModel>>) {
        try {
            val events = api.events()
            callback.onDataLoaded(events)
        } catch (e: Exception) {
            val events = gson.fromJson(fallbackData, Array<EventApiModel>::class.java)
            callback.onDataLoaded(events)
        }
    }
}