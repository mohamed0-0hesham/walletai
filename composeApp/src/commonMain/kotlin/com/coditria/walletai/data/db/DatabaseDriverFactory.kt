package com.coditria.walletai.data.db

import app.cash.sqldelight.db.SqlDriver

/**
 * Platform-specific bridge that opens the SQLite file. Android needs a [Context];
 * iOS uses NativeSqliteDriver with no extra inputs. The shared code only ever
 * sees this contract, never the platform driver type.
 */
expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}

const val DATABASE_FILE_NAME = "wallet.db"
