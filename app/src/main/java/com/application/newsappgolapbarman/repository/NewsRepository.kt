package com.application.newsappgolapbarman.repository

import com.application.newsappgolapbarman.model.Article
import com.application.newsappgolapbarman.repository.db.ArticleDatabase
import com.application.newsappgolapbarman.repository.service.RetrofitClient

class NewsRepository(val db :ArticleDatabase) {
    suspend fun getBreakingNews(country:String,language:String,pageNo:Int)=
        RetrofitClient.api.getTopHeadlines(country,language,pageNo)

    suspend fun getSearchNews(q:String,pageNo:Int)=
        RetrofitClient.api.getSearchNews(searchQuery = q,pageNumber = pageNo)

    suspend fun upsert(article: Article) = db.getDao().insert(article)
    suspend fun delete(article: Article) = db.getDao().delete(article)

    fun getAllArticles() = db.getDao().getNews()
}