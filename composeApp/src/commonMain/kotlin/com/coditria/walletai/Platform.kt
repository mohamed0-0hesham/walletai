package com.coditria.walletai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform