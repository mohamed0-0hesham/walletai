package com.coditria.walletai.data.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

/**
 * Android driver — backed by [AndroidSqliteDriver] which uses the OS SQLite.
 * Pass the application [Context] in once from [com.coditria.walletai.MainActivity].
 */
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver = AndroidSqliteDriver(
        schema = WalletDatabase.Schema,
        context = context,
        name = DATABASE_FILE_NAME,
    )
}
