package com.application.newsappgolapbarman.repository.db

import androidx.room.TypeConverter
import com.application.newsappgolapbarman.model.Source

class Convertor {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}