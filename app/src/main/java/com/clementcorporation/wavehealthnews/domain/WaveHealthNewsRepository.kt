package com.clementcorporation.wavehealthnews.domain

import com.clementcorporation.wavehealthnews.data.dtos.NewsListResponseDto
import kotlinx.coroutines.flow.Flow

interface WaveHealthNewsRepository {
    fun getNews(page: Int): Flow<NewsListResponseDto>
}