package com.e.brastlewark.di

import org.koin.dsl.module
import com.e.brastlewark.data.repository.GnomeRepository
import com.e.brastlewark.domain.usecase.GetList
import com.e.brastlewark.view.adapter.GnomeAdapter
import com.e.brastlewark.viewmodel.MainViewModel

val repositoriesModule = module {

    single<GnomeRepository> {(get()) }
    factory { GnomeAdapter(get(),get(),get()) }
}

val viewModelModule = module {
    single { MainViewModel(get()) }
}

val useCasesModule = module {
    single { GetList(get()) }

}






