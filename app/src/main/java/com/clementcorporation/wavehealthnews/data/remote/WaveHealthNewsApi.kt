package com.clementcorporation.wavehealthnews.data.remote
import com.clementcorporation.wavehealthnews.BuildConfig
import com.clementcorporation.wavehealthnews.data.dtos.NewsListResponseDto
import com.clementcorporation.wavehealthnews.util.Constants.DEFAULT_API_RESPONSE_COUNTRY
import com.clementcorporation.wavehealthnews.util.Constants.DEFAULT_API_RESPONSE_PAGE_SIZE
import com.clementcorporation.wavehealthnews.util.Constants.DEFAULT_API_RESPONSE_SORT
import com.clementcorporation.wavehealthnews.util.Constants.PARAM_API_KEY
import com.clementcorporation.wavehealthnews.util.Constants.PARAM_PAGE
import com.clementcorporation.wavehealthnews.util.Constants.PARAM_PAGE_SIZE
import com.clementcorporation.wavehealthnews.util.Constants.PARAM_QUERY
import com.clementcorporation.wavehealthnews.util.Constants.PARAM_SORT_BY
import retrofit2.http.GET
import retrofit2.http.Query

interface WaveHealthNewsApi {
    @GET("everything?&&&&")
    suspend fun getNewsList(
        @Query(PARAM_QUERY) q: String = DEFAULT_API_RESPONSE_COUNTRY,
        @Query(PARAM_API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(PARAM_SORT_BY) sortBy: String = DEFAULT_API_RESPONSE_SORT,
        @Query(PARAM_PAGE_SIZE) pageSize: Int = DEFAULT_API_RESPONSE_PAGE_SIZE,
        @Query(PARAM_PAGE) page: Int): NewsListResponseDto
}