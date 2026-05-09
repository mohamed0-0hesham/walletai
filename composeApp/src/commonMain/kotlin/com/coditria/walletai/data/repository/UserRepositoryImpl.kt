package com.coditria.walletai.data.repository

import com.coditria.walletai.data.mapper.toDomain
import com.coditria.walletai.data.source.local.UserLocalDataSource
import com.coditria.walletai.domain.model.User
import com.coditria.walletai.domain.repository.UserRepository

/**
 * Concrete repository wired to a [UserLocalDataSource]. Adding a remote source
 * later (e.g. for sync) is just adding a constructor parameter and writing a
 * cache-aside policy here — the contract above stays unchanged.
 */
class UserRepositoryImpl(
    private val local: UserLocalDataSource,
) : UserRepository {
    override suspend fun currentUser(): User = local.currentUser().toDomain()
}
