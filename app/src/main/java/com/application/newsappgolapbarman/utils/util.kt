package com.application.newsappgolapbarman.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView

import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.application.newsappgolapbarman.R
import com.application.newsappgolapbarman.model.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


//share news
fun shareNews(context: Context?,article: Article){
    val intent = Intent().apply {
        action= Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TITLE,article.title)
        putExtra(Intent.EXTRA_STREAM,article.urlToImage)
        putExtra(Intent.EXTRA_TEXT,article.urlToImage)
        type ="image/*"
    }
    context?.startActivity(Intent.createChooser(intent,"Share News Via"))
}
//load image in Image View
fun getCircularDrawable(context:Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth =8f
        centerRadius = 48f
        setTint(context.resources.getColor(R.color.bgLineColor))
        start()
    }
}
fun ImageView.loadImg(url:String?,progressDrawable:CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_foreground)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}
fun ImageView.loadingImg(imageView: ImageView, url: String?){
    if(url!=null){
        imageView.loadImg(url, getCircularDrawable(imageView.context))
    }
}