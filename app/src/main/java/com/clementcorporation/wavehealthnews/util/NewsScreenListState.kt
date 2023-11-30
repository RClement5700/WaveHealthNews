package com.clementcorporation.wavehealthnews.util

enum class NewsScreenListState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
}