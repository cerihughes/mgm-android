package uk.co.cerihughes.mgm.android.repository.local

import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.Before

class LocalDataSourceImplTest {

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        val config = RealmConfiguration.Builder()
            .name("LocalDataSourceImplTest.realm")
            .inMemory()
            .build()
        val realm = Realm.getInstance(config)

        localDataSource = LocalDataSourceImpl(realm)
    }
}