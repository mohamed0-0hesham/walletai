package com.coditria.walletai.data.source.local.sqldelight

import com.coditria.walletai.data.db.WalletDatabase
import com.coditria.walletai.data.entity.UserEntity
import com.coditria.walletai.data.source.local.UserLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SqlDelightUserLocalDataSource(
    private val db: WalletDatabase,
) : UserLocalDataSource {

    override suspend fun currentUser(): UserEntity = withContext(Dispatchers.Default) {
        val row = db.userQueries.selectCurrent().executeAsOne()
        UserEntity(
            id = row.id,
            firstName = row.firstName,
            lastName = row.lastName,
            email = row.email,
            avatarInitial = row.avatarInitial,
            tier = row.tier,
        )
    }
}
