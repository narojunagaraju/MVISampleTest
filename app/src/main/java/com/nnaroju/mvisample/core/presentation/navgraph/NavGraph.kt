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
import com.nnaroju.mvisample.addtoitem.presentation.AddToDoItemScreen
import com.nnaroju.mvisample.searchitems.presentation.TodoSearchScreen
import com.nnaroju.mvisample.todohome.presentation.TodoHomeScreen

const val REFRESH_CONTENT = "refreshContent"
const val ERROR_MESSAGE = "errorMessage"

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
            val refreshContent = navController.currentBackStackEntry
                ?.savedStateHandle
                ?.getStateFlow(REFRESH_CONTENT, false)

            val errorMessage = navController.currentBackStackEntry
                ?.savedStateHandle
                ?.getStateFlow(ERROR_MESSAGE, "")

            TodoHomeScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onNavigateToAddNote = {
                    navController.navigate(route = Route.AddTodoItemScreen.route)
                },
                onNavigateToSearch = {
                    navController.navigate(route = Route.SearchTodoItemScreen.route)
                },
                refreshContent = refreshContent?.value ?: false,
                errorMessage = errorMessage?.value ?: ""
            )
        }

        composable(route = Route.AddTodoItemScreen.route) {
            AddToDoItemScreen(
                modifier = Modifier.padding(innerPadding),
                navigateBack = { errorMessage ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        ERROR_MESSAGE,
                        errorMessage
                    )
                    navController.navigateUp()
                },
                navigateToHome = {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        REFRESH_CONTENT,
                        true
                    )
                    navController.navigateUp()
                }
            )
        }

        composable(route = Route.SearchTodoItemScreen.route) {
            TodoSearchScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}