package com.clementcorporation.wavehealthnews.presentation.news_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clementcorporation.wavehealthnews.data.dtos.Article
import com.clementcorporation.wavehealthnews.domain.WaveHealthNewsRepository
import com.clementcorporation.wavehealthnews.util.Constants.API_RESPONSE_OK
import com.clementcorporation.wavehealthnews.util.Constants.DEFAULT_API_RESPONSE_PAGE
import com.clementcorporation.wavehealthnews.util.Constants.DEFAULT_API_RESPONSE_PAGE_SIZE
import com.clementcorporation.wavehealthnews.util.NewsScreenListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: WaveHealthNewsRepository
): ViewModel() {
    val newsList = mutableStateListOf<Article>()
    private var page by mutableIntStateOf(1)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(NewsScreenListState.IDLE)

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        if (page == DEFAULT_API_RESPONSE_PAGE || (page != DEFAULT_API_RESPONSE_PAGE && canPaginate) && listState == NewsScreenListState.IDLE) {
            listState = if (page == DEFAULT_API_RESPONSE_PAGE) NewsScreenListState.LOADING else NewsScreenListState.PAGINATING
            repository.getNews(page).collectLatest {
                if (it.status == API_RESPONSE_OK) {
                    canPaginate = it.articles.size == DEFAULT_API_RESPONSE_PAGE_SIZE
                    if (page == DEFAULT_API_RESPONSE_PAGE) {
                        newsList.clear()
                        newsList.addAll(it.articles)
                    } else {
                        newsList.addAll(it.articles)
                    }
                    listState = NewsScreenListState.IDLE
                    if (canPaginate) page++
                } else {
                    listState =
                        if (page == DEFAULT_API_RESPONSE_PAGE) NewsScreenListState.ERROR else NewsScreenListState.PAGINATION_EXHAUST
                }
            }
        }
    }

    override fun onCleared() {
        page = DEFAULT_API_RESPONSE_PAGE
        listState = NewsScreenListState.IDLE
        canPaginate = false
        super.onCleared()
    }
}