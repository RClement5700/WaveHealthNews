package com.clementcorporation.wavehealthnews.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.clementcorporation.wavehealthnews.data.dtos.Article
import com.clementcorporation.wavehealthnews.presentation.news_details.NewsDetailsScreen
import com.clementcorporation.wavehealthnews.presentation.news_list.NewsListScreen
import com.clementcorporation.wavehealthnews.util.ArticleNavType
import com.clementcorporation.wavehealthnews.util.Constants.ROUTE_EXT_ARTICLE
import com.clementcorporation.wavehealthnews.util.WaveHealthNewsScreens

@Composable
fun WaveHealthNewsNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = WaveHealthNewsScreens.NewsList.name) {

        composable(WaveHealthNewsScreens.NewsList.name) {
            NewsListScreen(navController)
        }
        composable(
            route = "${WaveHealthNewsScreens.NewsDetails.name}/{$ROUTE_EXT_ARTICLE}",
            arguments = listOf(
                navArgument(ROUTE_EXT_ARTICLE) {
                    type = ArticleNavType()
                }
            )
        ) { backstackEntry ->
            val article = backstackEntry.arguments?.getParcelable<Article>(ROUTE_EXT_ARTICLE)
            NewsDetailsScreen(article, navController)
        }
    }
}