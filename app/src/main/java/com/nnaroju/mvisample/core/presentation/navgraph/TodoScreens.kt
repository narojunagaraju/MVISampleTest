package com.nnaroju.mvisample.core.presentation.navgraph

sealed class Route(
    val route: String
) {
    object AddTodoItemScreen : Route(route = "addTodoItem")
    object SearchTodoItemScreen : Route(route = "searchTodoItem")
    object TodoHomeScreen : Route(route = "todoHome")
}