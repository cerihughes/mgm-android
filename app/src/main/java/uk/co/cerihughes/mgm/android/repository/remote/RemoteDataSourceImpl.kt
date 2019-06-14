package uk.co.cerihughes.mgm.android.repository.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uk.co.cerihughes.mgm.android.model.Event

class RemoteDataSourceImpl : RemoteDataSource {

    override fun getRemoteData(callback: RemoteDataSource.GetRemoteDataCallback<List<Event>>) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mgm-gcp.appspot.com")
            .build()

        val service = retrofit.create<RetrofitEventService>(RetrofitEventService::class.java)
        service.getRemoteEvents().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                response.body()?.let {
                    callback.onDataLoaded(it)
                } ?: callback.onDataNotAvailable()
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                callback.onDataNotAvailable()
            }
        })
    }
}