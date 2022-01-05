package com.application.newsappgolapbarman.model

import com.google.gson.annotations.SerializedName

data class newsResponse(
    @SerializedName("articles")
    var articles : MutableList<Article>,
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int?
        )