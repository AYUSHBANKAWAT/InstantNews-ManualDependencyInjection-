package com.application.newsappgolapbarman.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.application.newsappgolapbarman.repository.db.Convertor
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(
    tableName = "articles"
)
@TypeConverters(Convertor::class)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @SerializedName("author")
    var author : String?,
    @SerializedName("content")
    var content : String?,
    @SerializedName("description")
    var description : String?,
    @SerializedName("publishedAt")
    var publishedAt : String?,
    @SerializedName("source")
    var source : Source?,
    @SerializedName("title")
    var title : String?,
    @SerializedName("url")
    var url : String?,
    @SerializedName("urlToImage")
    var urlToImage : String?
) : Serializable