package com.nnaroju.mvisample.core.presentation.navgraph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nnaroju.mvisample.todohome.domain.presentation.TodoHomeScreen


@Composable
fun NavGraph(
    startDestination: String,
    innerPadding: PaddingValues
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(
            route = Route.TodoHomeScreen.route
        ) {
            TodoHomeScreen(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
                onNavigateToAddNote = {
                    navController.navigate(route = Route.AddTodoItemScreen.route)
                })
        }

        composable(route = Route.AddTodoItemScreen.route) {
            Text(text = "Hello Add Note")
        }

        composable(route = Route.SearchTodoItemScreen.route) {
            Text(text = "Hey Search screen")
        }
    }
}