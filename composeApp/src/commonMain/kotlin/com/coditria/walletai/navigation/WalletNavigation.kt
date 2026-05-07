package com.coditria.walletai.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

sealed class Route(val key: String) {
    data object Splash : Route("splash")
    data object SignIn : Route("signin")
    data object SignUp : Route("signup")
    data object Dashboard : Route("dashboard")
    data object AddTransaction : Route("add")
    data object Voice : Route("voice")
    data object Installments : Route("installments")
    data object Reports : Route("reports")
    data object Settings : Route("settings")
}

class WalletNavController internal constructor(initial: Route) {
    var current: Route by mutableStateOf(initial)
        private set

    private val backstack = ArrayDeque<Route>()

    fun navigate(route: Route) {
        if (current == route) return
        backstack.addLast(current)
        current = route
    }

    fun replace(route: Route) {
        backstack.clear()
        current = route
    }

    fun back() {
        val previous = backstack.removeLastOrNull() ?: return
        current = previous
    }
}

@Composable
fun rememberWalletNavController(initial: Route = Route.Splash): WalletNavController =
    remember { WalletNavController(initial) }

@Composable
fun WalletNavHost(
    controller: WalletNavController,
    content: @Composable (Route) -> Unit,
) {
    AnimatedContent(
        targetState = controller.current,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "wallet-nav",
    ) { route -> content(route) }
}
