package com.clementcorporation.wavehealthnews.presentation.main

import android.os.Bundle
import androidx.navigation.NavType
import com.clementcorporation.wavehealthnews.data.dtos.Article
import com.google.gson.Gson

class ArticleNavType : NavType<Article>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Article? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Article {
        return Gson().fromJson(value, Article::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Article) {
        bundle.putParcelable(key, value)
    }
}