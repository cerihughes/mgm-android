package uk.co.cerihughes.mgm.android.ui

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun RemoteDataLoadingViewModel.loadDataSync() {
    val latch = CountDownLatch(1)
    loadData(object : RemoteDataLoadingViewModel.LoadDataCallback {
        override fun onDataLoaded() {
            latch.countDown()
        }
    })
    latch.await(1, TimeUnit.SECONDS)
}
