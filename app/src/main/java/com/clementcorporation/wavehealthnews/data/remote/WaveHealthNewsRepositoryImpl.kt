package com.clementcorporation.wavehealthnews.data.remote

import com.clementcorporation.wavehealthnews.data.dtos.NewsListResponseDto
import com.clementcorporation.wavehealthnews.domain.WaveHealthNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WaveHealthNewsRepositoryImpl @Inject constructor(private val api: WaveHealthNewsApi):
    WaveHealthNewsRepository {

    override fun getNews(page: Int): Flow<NewsListResponseDto> = flow {
        try {
            emit(api.getNewsList(page = page))
        } catch (error: Exception) {
            emit(NewsListResponseDto(emptyList(), error.message ?: "", 0))
        }
    }.flowOn(Dispatchers.IO)
}