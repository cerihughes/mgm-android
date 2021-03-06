package uk.co.cerihughes.mgm.android

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.android.startKoin
import uk.co.cerihughes.mgm.android.di.appModule

class MGMApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
    }
}
