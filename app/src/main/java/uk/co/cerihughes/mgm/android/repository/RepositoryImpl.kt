package uk.co.cerihughes.mgm.android.repository

import android.content.Context
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.local.LocalDataSource
import uk.co.cerihughes.mgm.android.repository.remote.RemoteDataSource

class RepositoryImpl(
    context: Context,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    Repository {

    private val fallbackData: String
    private val gson = GsonFactory.createGson()

    init {
        fallbackData = context.resources.openRawResource(R.raw.mgm).bufferedReader().use { it.readText() }
    }

    override fun getEvents(callback: Repository.GetOperationCallback<List<Event>>) {
        remoteDataSource.getRemoteData(object : RemoteDataSource.GetRemoteDataCallback<List<EventApiModel>> {
            override fun onDataLoaded(data: List<EventApiModel>) {
                val models = data.map { it.toDataModel() }
                localDataSource.addEvents(models)
                callback.onDataLoaded(models)
            }

            override fun onDataNotAvailable() {
                val apiModels = gson.fromJson(fallbackData, Array<EventApiModel>::class.java)
                val models = apiModels.map { it.toDataModel() }
                callback.onDataLoaded(models.toList())
            }
        })
    }
}