package com.application.newsappgolapbarman.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.application.newsappgolapbarman.model.Article
import com.application.newsappgolapbarman.model.newsResponse
import com.application.newsappgolapbarman.repository.NewsRepository
import com.application.newsappgolapbarman.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
):ViewModel() {
    val breakingNews: MutableLiveData<Resource<newsResponse>> = MutableLiveData()
    var breakingNewsPageNo = 1
    var breakingnewsResponse: newsResponse?=null

    //search news
    val searchNews: MutableLiveData<Resource<newsResponse>> = MutableLiveData()
    var searchNewsPageNo = 1
    var searchNewsResponse: newsResponse? = null

    lateinit var articles: LiveData<PagedList<Article>>
    init {
        getBreakingNews("in")
    }

    private fun getBreakingNews(countrycode:String)= viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response =newsRepository.getBreakingNews(countrycode,"en",breakingNewsPageNo)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<newsResponse>): Resource<newsResponse>? {
        if(response.isSuccessful){
            response.body()?.let {
                breakingNewsPageNo++
                if(breakingnewsResponse==null){
                    breakingnewsResponse=it
                }
                else{
                    val oldArticles = breakingnewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingnewsResponse?:it)
            }
        }
        return Resource.Error(response.message().toString())
    }

    fun getSearchNews(q:String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val searchNewsResponse = newsRepository.getSearchNews(q,searchNewsPageNo)
        searchNews.postValue(handleSearchNewsResponse(searchNewsResponse))
    }

    private fun handleSearchNewsResponse(response: Response<newsResponse>): Resource<newsResponse>? {
        if(response.isSuccessful){
            response.body()?.let {
                searchNewsPageNo++
                if(searchNewsResponse==null){
                    searchNewsResponse=it
                }
                else{
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse?:it)
            }
        }
        return Resource.Error(response.message())
    }
    fun insertArticles(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun deleteArticle(article: Article)=viewModelScope.launch {
        newsRepository.delete(article)
    }
    fun getSavedArticles() = newsRepository.getAllArticles()
    fun getBreakingNews():LiveData<PagedList<Article>>{
        return articles
    }
}