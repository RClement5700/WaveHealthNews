package com.clementcorporation.wavehealthnews.presentation.news_list

enum class NewsScreenListState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
}