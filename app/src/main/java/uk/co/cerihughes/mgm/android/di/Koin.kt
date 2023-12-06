package uk.co.cerihughes.mgm.android.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uk.co.cerihughes.mgm.android.repository.Repository
import uk.co.cerihughes.mgm.android.repository.RepositoryImpl
import uk.co.cerihughes.mgm.android.repository.fallback.FallbackDataSource
import uk.co.cerihughes.mgm.android.repository.fallback.FallbackDataSourceImpl
import uk.co.cerihughes.mgm.android.ui.MainViewModel
import uk.co.cerihughes.mgm.android.ui.albumscores.AlbumScoresViewModel
import uk.co.cerihughes.mgm.android.ui.latestevent.LatestEventViewModel

val appModule = module {

    // single instance of FallbackDataSource
    single<FallbackDataSource> { FallbackDataSourceImpl(androidContext()) }

    // single instance of Repository
    single<Repository> { RepositoryImpl(get()) }

    viewModel { MainViewModel() }
    viewModel { LatestEventViewModel(get()) }
    viewModel { AlbumScoresViewModel(get()) }
}
