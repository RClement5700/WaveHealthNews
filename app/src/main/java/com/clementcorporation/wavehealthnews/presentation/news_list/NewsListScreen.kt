package com.clementcorporation.wavehealthnews.presentation.news_list

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.clementcorporation.wavehealthnews.R
import com.clementcorporation.wavehealthnews.data.dtos.toNewListItem
import com.clementcorporation.wavehealthnews.util.NewsListItemTile
import com.clementcorporation.wavehealthnews.util.WaveHealthNewsScreens
import com.clementcorporation.wavehealthnews.util.WaveNewsToolBar
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun NewsListScreen(navController: NavHostController) {
    val viewModel: NewsListViewModel = hiltViewModel()
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: - 9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }
    val articles = viewModel.newsList
    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == NewsScreenListState.IDLE)
            viewModel.getNews()
    }
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(color = Color.White),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp,
        tonalElevation = 4.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        )
        {
            Spacer(modifier = Modifier.height(4.dp))
            WaveNewsToolBar(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                navController = navController,
                showBackButton = false,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(state = lazyColumnListState) {
                items(items = articles) { article ->
                    NewsListItemTile(newsListItem = article.toNewListItem()) {
                        val articleJson = Uri.encode(Gson().toJson(article))
                        navController.navigate("${WaveHealthNewsScreens.NewsDetails.name}/$articleJson")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
                item(
                    key = viewModel.listState,
                ) {
                    when (viewModel.listState) {
                        NewsScreenListState.LOADING -> {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = stringResource(R.string.news_list_screen_loading_content)
                                )
                                CircularProgressIndicator(color = Color.Black)
                            }
                        }

                        NewsScreenListState.PAGINATING -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(text = stringResource(R.string.news_list_screen_loading_content))
                                CircularProgressIndicator(color = Color.Black)
                            }
                        }

                        NewsScreenListState.PAGINATION_EXHAUST -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp, vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(imageVector = Icons.Rounded.Face, contentDescription = "Dissatisfied Face")
                                Text(text = stringResource(R.string.news_list_screen_no_content))
                                TextButton(
                                    modifier = Modifier
                                        .padding(top = 8.dp),
                                    onClick = {
                                        coroutineScope.launch {
                                            lazyColumnListState.animateScrollToItem(0)
                                        }
                                    },
                                    content = {
                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.KeyboardArrowUp,
                                                contentDescription = "Click to return to the top of the page"
                                            )
                                            Text(text = stringResource(id = R.string.news_list_screen_back_to_top))
                                            Icon(
                                                imageVector = Icons.Rounded.KeyboardArrowUp,
                                                contentDescription = "Click to return to the top of the page"
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}