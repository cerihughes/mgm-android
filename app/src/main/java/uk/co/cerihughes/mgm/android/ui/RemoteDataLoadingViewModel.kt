package uk.co.cerihughes.mgm.android.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.Repository

abstract class RemoteDataLoadingViewModel(private val repository: Repository) : ViewModel() {

    private val backgroundThreadHandler = Handler()
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    interface LoadDataCallback {
        fun onDataLoaded()
    }

    fun loadData(callback: LoadDataCallback) {
        backgroundThreadHandler.post {
            repository.getEvents(object : Repository.GetOperationCallback<List<Event>> {
                override fun onDataLoaded(data: List<Event>) {
                    mainThreadHandler.post {
                        setEvents(data)
                        callback.onDataLoaded()
                    }
                }
            })
        }
    }

    abstract fun setEvents(events: List<Event>)
}