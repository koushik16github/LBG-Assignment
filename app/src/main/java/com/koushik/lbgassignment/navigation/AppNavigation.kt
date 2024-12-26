package com.koushik.lbgassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.koushik.lbgassignment.ui.screen.ErrorMessage
import com.koushik.lbgassignment.ui.screen.ItemDetailScreen
import com.koushik.lbgassignment.ui.screen.ItemListScreen
import com.koushik.lbgassignment.viewmodel.MainViewModel

@Composable
fun AppNavigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = "item_list_screen"
    ) {
        composable("item_list_screen") {
            ItemListScreen(viewModel = viewModel) { selectedItem ->
                navController.navigate("item_detail_screen/${selectedItem.articleId}")
            }
        }
        composable(
            route = "item_detail_screen/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            val selectedItem = viewModel.getItemById(itemId)
            selectedItem?.let {
                ItemDetailScreen(item = it)
            } ?: ErrorMessage(error = "Item not found", onRetry = { navController.popBackStack() })
        }
    }
}