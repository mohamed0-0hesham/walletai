package com.coditria.walletai.domain.repository

import com.coditria.walletai.domain.model.User

/**
 * Read access to the currently signed-in user. Pure abstraction — UI/presentation
 * code depends on this, never on a concrete data source. Swap the implementation
 * (in-memory, SQLDelight, Room, network) without touching the domain.
 */
interface UserRepository {
    suspend fun currentUser(): User
}
