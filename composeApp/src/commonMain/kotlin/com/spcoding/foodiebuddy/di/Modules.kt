package com.spcoding.foodiebuddy.di

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.spcoding.foodiebuddy.core.data.HttpClientFactory
import com.spcoding.foodiebuddy.recipe.data.database.DatabaseFactory
import com.spcoding.foodiebuddy.recipe.data.database.FavoriteCharacterDatabase
import com.spcoding.foodiebuddy.recipe.data.network.KtorRemoteCharacterDataSource
import com.spcoding.foodiebuddy.recipe.data.network.RemoteCharacterDataSource
import com.spcoding.foodiebuddy.recipe.data.repository.DisneyCharacterRepository
import com.spcoding.foodiebuddy.recipe.domain.disney.CharacterRepository
import com.spcoding.foodiebuddy.recipe.presentation.disney.SelectedCharacterViewModel
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail.CharacterDetailViewModel
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.CharacterListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule : Module

val sharedModules = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteCharacterDataSource).bind<RemoteCharacterDataSource>()
    singleOf(::DisneyCharacterRepository).bind<CharacterRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single {
        get<FavoriteCharacterDatabase>().characterDao
    }

    viewModelOf(::CharacterListViewModel)
    viewModelOf(::CharacterDetailViewModel)
    viewModelOf(::SelectedCharacterViewModel)
}