package com.clementcorporation.wavehealthnews.data.dtos

import android.os.Parcelable
import com.clementcorporation.wavehealthnews.domain.NewsListItem
import com.clementcorporation.wavehealthnews.util.Constants.API_DATE_PATTERN
import com.clementcorporation.wavehealthnews.util.Constants.US_DATE_PATTERN
import kotlinx.parcelize.Parcelize
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewsListResponseDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int,
)

@Parcelize
data class Source(
    val id: String = "",
    val name: String = ""
): Parcelable

@Parcelize
data class Article(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: Source = Source(),
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
): Parcelable

fun Article.toNewListItem(): NewsListItem {
    val us = Locale.US
    val format = SimpleDateFormat(API_DATE_PATTERN, us)
    var publishedDate = ""
    try {
        val date: Date? = format.parse(publishedAt)
        publishedDate = date?.let { SimpleDateFormat(US_DATE_PATTERN, us).format(it) } ?: ""
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return NewsListItem(
        title = title,
        date = publishedDate,
        thumbnail = urlToImage
    )
}