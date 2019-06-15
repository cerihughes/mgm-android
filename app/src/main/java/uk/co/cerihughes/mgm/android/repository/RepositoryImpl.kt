package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.local.LocalDataSource
import uk.co.cerihughes.mgm.android.repository.remote.RemoteDataSource

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    private var cachedEvents: List<Event>? = null

    override fun getEvents(callback: Repository.GetOperationCallback<List<Event>>) {
        cachedEvents?.let {
            callback.onDataLoaded(it)
        } ?: loadEvents(callback)
    }

    private fun loadEvents(callback: Repository.GetOperationCallback<List<Event>>) {
        remoteDataSource.getRemoteData(object : RemoteDataSource.GetRemoteDataCallback<List<Event>> {
            override fun onDataLoaded(data: List<Event>) {
                localDataSource.setEvents(data)
                cachedEvents = data
                callback.onDataLoaded(data)
            }

            override fun onDataNotAvailable() {
                val localData = localDataSource.getEvents()
                callback.onDataLoaded(localData)
            }
        })
    }
}