package com.application.newsappgolapbarman.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.newsappgolapbarman.R
import com.application.newsappgolapbarman.model.Article
import com.application.newsappgolapbarman.utils.loadingImg

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    companion object{
        private val diffUtilCallback = object:DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title==oldItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return newItem.title==newItem.title
            }

        }
    }

    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(item:Article){
            val img = itemView.findViewById<ImageView>(R.id.ivArticle)
            val title = itemView.findViewById<TextView>(R.id.tvArticleTitle)
            val source = itemView.findViewById<TextView>(R.id.tvSource)
            val publishedAt = itemView.findViewById<TextView>(R.id.TvPublishedAt)
            title.text = item.title
            source.text=item.source?.name.toString()?:" "
            publishedAt.text = item.publishedAt
           // var progressBar =itemView.findViewById<CircularProgressDrawable>(R.id.progressBar)
            img.loadingImg(img,item.urlToImage)
        }
    }
    val differ = AsyncListDiffer(this, diffUtilCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
       return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_card,parent,false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
        val itemSave = holder.itemView.findViewById<ImageView>(R.id.ivSave)
        val itemShare= holder.itemView.findViewById<ImageView>(R.id.ivShare)
        itemSave.setOnClickListener {
            if( itemSave.tag.toString().toInt()==0){
                itemSave.tag =1
                itemSave.setImageDrawable( it.resources.getDrawable(R.drawable.ic_baseline_bookmark_24))
                onArticleSaveClick?.let {
                    if(article!=null){
                        it(article)
                    }
                }
            }
            else{
                itemSave.setImageDrawable(it.resources.getDrawable(R.drawable.ic_baseline_bookmark_border_24))
                itemSave.tag=0
                onArticleDeleteClick?.let {
                    if(article!=null){
                        it(article)
                    }
                }
            }

            onArticleSaveClick?.let {
                article?.let { it1 -> it(it1) }
            }

        }
        itemShare.setOnClickListener {
            onItemClickListener?.let {
                article.let {it1->
                    it(it1)
                }
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                article.let {
                    it(article)
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    var isSave =false
    private var onItemClickListener:((Article)->Unit)?=null
    private var onArticleSaveClick:((Article)->Unit)?=null
    private var onArticleDeleteClick:((Article)->Unit)?=null
    private var onArticleShareClick:((Article)->Unit)?=null

    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener=listener
    }
    fun onSaveItemClickListener(listener:(Article)->Unit){
        onArticleSaveClick=listener
    }
    fun onDeleteItemClickListener(listener:(Article)->Unit){
        onArticleDeleteClick=listener
    }
    fun onShareItemClickListener(listener:(Article)->Unit){
        onArticleShareClick=listener
    }



}