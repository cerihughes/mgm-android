package uk.co.cerihughes.mgm.android.repository

import androidx.lifecycle.LiveData
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.local.LocalDataSource
import uk.co.cerihughes.mgm.android.repository.remote.RemoteDataSource

class RepositoryImpl(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) :
    Repository {

    override fun getEvents(callback: Repository.GetOperationCallback<LiveData<List<Event>>>) {
        callback.onLocalData(localDataSource.getEvents())

        remoteDataSource.getRemoteData(object : RemoteDataSource.GetRemoteDataCallback<Array<EventApiModel>> {
            override fun onDataLoaded(data: Array<EventApiModel>) {
                localDataSource.addEvents(data.map { it.toDataModel() })
                callback.onRemoteLoad()
            }
        })
    }
}