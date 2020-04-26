package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.fallback.FallbackDataSource
import uk.co.cerihughes.mgm.android.repository.local.LocalDataSource
import uk.co.cerihughes.mgm.android.repository.remote.RemoteDataSource

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val fallbackDataSource: FallbackDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    private var cachedEvents: List<Event>? = null
    override fun getEvents(callback: Repository.GetOperationCallback<List<Event>>) {
        cachedEvents?.let {
            callback.onDataLoaded(it)
        } ?: loadEvents(callback)
    }

    private fun loadEvents(callback: Repository.GetOperationCallback<List<Event>>) {
        remoteDataSource.getRemoteData(object : RemoteDataSource.GetRemoteDataCallback<List<EventApiModel>> {
            override fun onDataLoaded(data: List<EventApiModel>) {
                val models = data.map { it.toDataModel() }
                localDataSource.addEvents(models)
                cachedEvents = models
                callback.onDataLoaded(models)
            }

            override fun onDataNotAvailable() {
                val apiModels = fallbackDataSource.getFallbackData()
                val models = apiModels.map { it.toDataModel() }
                callback.onDataLoaded(models.toList())
            }
        })
    }
}
