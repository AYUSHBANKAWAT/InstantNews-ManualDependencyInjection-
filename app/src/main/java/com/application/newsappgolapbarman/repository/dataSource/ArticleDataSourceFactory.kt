package com.application.newsappgolapbarman.repository.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.application.newsappgolapbarman.model.Article
import kotlinx.coroutines.CoroutineScope

class ArticleDataSourceFactory(private val scope:CoroutineScope):DataSource.Factory<Int,Article>() {
    val articleDataSource = MutableLiveData<ArticleDataSource>()
    override fun create(): DataSource<Int, Article> {
        val newsArticleDataSource =ArticleDataSource(scope)
        articleDataSource.postValue(newsArticleDataSource)
        return newsArticleDataSource
    }
}