package com.application.newsappgolapbarman.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.newsappgolapbarman.MainActivity
import com.application.newsappgolapbarman.R
import com.application.newsappgolapbarman.adapter.ArticleAdapter
import com.application.newsappgolapbarman.databinding.FragmentBreakingNewsBinding
import com.application.newsappgolapbarman.utils.Resource
import com.application.newsappgolapbarman.utils.shareNews
import com.application.newsappgolapbarman.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel :NewsViewModel
    lateinit var binding:FragmentBreakingNewsBinding
    var TAG ="BreakingNewsFragment"
    lateinit var newsAdapter:ArticleAdapter
    fun setUpRecyclerView(){
        newsAdapter = ArticleAdapter()
       binding.breakingNewsRv.apply {
         adapter = newsAdapter
         layoutManager = LinearLayoutManager(activity)
        }
       newsAdapter.onSaveItemClickListener {
           if(it.id==null){
               it.id= Random.nextInt(0,1000);
           }
           viewModel.insertArticles(it)
           Snackbar.make(requireView(),"Saved",Snackbar.LENGTH_SHORT).show()
       }
        newsAdapter.onDeleteItemClickListener {
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(),"Removed",Snackbar.LENGTH_SHORT).show()
        }
        newsAdapter.onShareItemClickListener {
            shareNews(context,it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {newsResponse->
            when(newsResponse){
                is Resource.Success ->{
                   binding.shimmerFrameLayout.stopShimmer()
                   binding.shimmerFrameLayout.visibility=View.GONE
                    newsResponse.data?.let {
                       binding.breakingNewsRv .visibility = View.VISIBLE
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error->{
                   binding.shimmerFrameLayout.visibility = View.GONE
                    newsResponse.message?.let {
                        Log.e(TAG,"Error:$it ")
                    }
                }
                is Resource.Loading->{
                   binding.shimmerFrameLayout.startShimmer()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBreakingNewsBinding.inflate(inflater,container,false)
        return binding.root
    }
}