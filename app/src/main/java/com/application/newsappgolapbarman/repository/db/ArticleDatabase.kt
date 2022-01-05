package com.application.newsappgolapbarman.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.application.newsappgolapbarman.model.Article

@Database(entities = [Article::class],version = 3,exportSchema = false)
@TypeConverters(Convertor::class)
abstract class ArticleDatabase:RoomDatabase() {
    abstract fun getDao():ArticleDao
    companion object{
        @Volatile
        private var articleDatabaseInstance: ArticleDatabase?=null
        private val LOCK= Any()
        operator fun invoke(context: Context) = articleDatabaseInstance?: synchronized(LOCK){
            articleDatabaseInstance?:createDatabase(context).also {
                articleDatabaseInstance=it
            }
        }
        private fun createDatabase(context: Context)= Room.databaseBuilder(context,
                ArticleDatabase::class.java,
                "artilcle_db.db"
            ).fallbackToDestructiveMigration()
            .build()
        }
    }
