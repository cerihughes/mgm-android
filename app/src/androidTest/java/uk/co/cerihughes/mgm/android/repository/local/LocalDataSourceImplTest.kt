package uk.co.cerihughes.mgm.android.repository.local

import androidx.test.platform.app.InstrumentationRegistry
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocalDataSourceImplTest {

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name("LocalDataSourceImplTest.realm")
            .inMemory()
            .build()
        val realm = Realm.getInstance(config)

        localDataSource = LocalDataSourceImpl(realm)
    }

    @Test
    fun placeholder() {
        Assert.assertTrue(true)
    }
}
