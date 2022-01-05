package com.application.newsappgolapbarman.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.application.newsappgolapbarman.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article:Article):Long

    @Query("SELECT * FROM articles")
    fun getNews():LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)

}