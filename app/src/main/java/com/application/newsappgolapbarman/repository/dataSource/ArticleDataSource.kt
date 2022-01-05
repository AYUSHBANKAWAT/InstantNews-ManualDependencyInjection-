package com.application.newsappgolapbarman.repository.dataSource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.application.newsappgolapbarman.model.Article
import com.application.newsappgolapbarman.model.newsResponse
import com.application.newsappgolapbarman.repository.service.RetrofitClient
import com.application.newsappgolapbarman.utils.Resource
import com.application.newsappgolapbarman.utils.constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ArticleDataSource(val scope :CoroutineScope) :PageKeyedDataSource<Int,Article>() {
    //breaking news
    val breakingNews:MutableLiveData<MutableList<Article>> = MutableLiveData()
    var breakingNewsPageNo = 1
    var breakingnewsResponse:newsResponse?=null

    //search news
    val searchNews:MutableLiveData<Resource<newsResponse>> = MutableLiveData()
    var searchNewsPageNo = 1
    var searchNewsResponse:newsResponse? = null
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            try {
                val response = RetrofitClient.api.getTopHeadlines("in","en",1,constants.API_KEY)
                when{
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            breakingNews.postValue(it)
                            callback.onResult(it, null, 2)
                        }
                    }
                }
            }catch (e:Exception) {
                Log.i("ArticleDataBase",e.message.toString())
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        try {
            scope.launch {
                val response = RetrofitClient.api.getTopHeadlines(
                    "in",
                    "en",
                    params.requestedLoadSize,
                    constants.API_KEY
                )
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            //  breakingNews.postValue(it)
                            callback.onResult(it, params.key + 1)
                        }
                    }
                }
            }
        }catch (e:Exception) {
            Log.i("ArticleDataBase",e.message.toString())
        }
    }
}