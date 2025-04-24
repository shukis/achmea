package com.achmea.di

import com.achmea.data.core.HttpClientFactory
import com.achmea.data.local.EmployersCache
import com.achmea.data.remote.EmployersApi
import com.achmea.data.repository.EmployerRepositoryImpl
import com.achmea.domain.repository.EmployersRepository
import com.achmea.domain.usecase.GetEmployerByIdUseCase
import com.achmea.domain.usecase.SearchEmployersUseCase
import com.achmea.presentation.list.EmployersListViewModel
import com.achmea.presentation.details.EmployerDetailsViewModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { HttpClientFactory.create(get()) }
    singleOf(::EmployersApi)
    singleOf(::EmployerRepositoryImpl).bind<EmployersRepository>()
    singleOf(::EmployersCache)
}

val viewModelModule = module {
    viewModelOf(::EmployersListViewModel)
    viewModelOf(::EmployerDetailsViewModel)
}

val useCaseModule = module {
    factoryOf(::SearchEmployersUseCase)
    factoryOf(::GetEmployerByIdUseCase)
}