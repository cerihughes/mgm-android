package uk.co.cerihughes.mgm.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uk.co.cerihughes.mgm.android.di.appModule

class MGMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MGMApplication)
            modules(listOf(appModule))
        }
    }
}
