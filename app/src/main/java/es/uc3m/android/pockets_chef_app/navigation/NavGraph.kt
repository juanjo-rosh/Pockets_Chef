package es.uc3m.android.pockets_chef_app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavGraph(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Home : NavGraph(
        route = "home",
        label = "Home",
        icon  = Icons.Default.Home
    )
    data object Recipes : NavGraph(
        route = "recipes",
        label = "Recipes",
        icon  = Icons.Default.Book
    )
    data object Pantry : NavGraph(
        route = "pantry",
        label = "Pantry",
        icon  = Icons.Default.Inventory2
    )
    data object Map : NavGraph(
        route = "map",
        label = "Map",
        icon  = Icons.Default.Map
    )
    data object Profile : NavGraph(
        route = "profile",
        label = "Profile",
        icon  = Icons.Default.Person
    )
}

val bottomNavItems = listOf(
    NavGraph.Home,
    NavGraph.Recipes,
    NavGraph.Pantry,
    NavGraph.Map,
    NavGraph.Profile
)
