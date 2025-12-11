package com.spcoding.foodiebuddy

import androidx.compose.ui.window.ComposeUIViewController
import com.spcoding.foodiebuddy.app.App
import com.spcoding.foodiebuddy.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }