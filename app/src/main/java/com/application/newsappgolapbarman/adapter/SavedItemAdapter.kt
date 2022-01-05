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

class SavedArticleAdapter : RecyclerView.Adapter<SavedArticleAdapter.SavedArticleViewHolder>() {
        private val diffUtilCallback = object:DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title==oldItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id==newItem.id
            }

        }


    inner class SavedArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArticleViewHolder {
        return SavedArticleViewHolder(LayoutInflater.from( parent.context).inflate(R.layout.item_saved,parent,false))
    }

    override fun onBindViewHolder(holder: SavedArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
        val itemSave = holder.itemView.findViewById<ImageView>(R.id.ivSave)
        val itemShare= holder.itemView.findViewById<ImageView>(R.id.ivShare)

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

    private var onItemClickListener:((Article)->Unit)?=null
    private var onArticleShareClick:((Article)->Unit)?=null

    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener=listener
    }
    fun onShareItemClickListener(listener:(Article)->Unit){
        onArticleShareClick=listener
    }



}