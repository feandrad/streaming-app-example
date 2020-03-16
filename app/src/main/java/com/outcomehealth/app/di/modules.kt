package com.outcomehealth.app.di

import com.outcomehealth.app.ui.gallery.GalleryAdapter
import com.outcomehealth.app.ui.gallery.GalleryViewModel
import com.outcomehealth.app.ui.player.PlayerViewModel
import com.outcomehealth.app.usecase.LoadVideoByTitleUseCase
import com.outcomehealth.app.usecase.LoadVideoGalleyUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module(override = true) {
    viewModel { GalleryViewModel(get()) }
    factory { GalleryAdapter() }
    single { LoadVideoGalleyUseCase(get()) }

    viewModel { PlayerViewModel(get()) }
    single { LoadVideoByTitleUseCase(get()) }
}