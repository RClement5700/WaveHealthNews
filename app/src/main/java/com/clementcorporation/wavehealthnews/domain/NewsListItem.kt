package com.clementcorporation.wavehealthnews.domain

data class NewsListItem(
    val title: String,
    val date: String?,
    val thumbnail: String? = null
)
