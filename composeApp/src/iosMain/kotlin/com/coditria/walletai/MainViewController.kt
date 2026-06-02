package com.coditria.walletai

import androidx.compose.ui.window.ComposeUIViewController
import com.coditria.walletai.data.db.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController {
    App(driverFactory = DatabaseDriverFactory())
}
