package com.clementcorporation.wavehealthnews.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.clementcorporation.wavehealthnews.data.dtos.Article
import com.clementcorporation.wavehealthnews.presentation.news_details.NewsDetailsScreen
import com.clementcorporation.wavehealthnews.presentation.news_list.NewsListScreen
import com.clementcorporation.wavehealthnews.util.Constants.ROUTE_EXT_ARTICLE
import com.clementcorporation.wavehealthnews.util.WaveHealthNewsScreens

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
            val context = navController.context
            val packageName = context.packageName
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val minSdkVersion = packageInfo.applicationInfo.minSdkVersion
            val article = if (minSdkVersion >= 33)
                backstackEntry.arguments?.getParcelable(ROUTE_EXT_ARTICLE, Article::class.java)
                else backstackEntry.arguments?.getParcelable(ROUTE_EXT_ARTICLE)
            NewsDetailsScreen(article, navController)
        }
    }
}