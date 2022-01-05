package com.application.newsappgolapbarman.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.newsappgolapbarman.MainActivity
import com.application.newsappgolapbarman.R
import com.application.newsappgolapbarman.adapter.ArticleAdapter
import com.application.newsappgolapbarman.databinding.FragmentSearchBinding
import com.application.newsappgolapbarman.utils.Resource
import com.application.newsappgolapbarman.utils.constants
import com.application.newsappgolapbarman.utils.shareNews
import com.application.newsappgolapbarman.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:NewsViewModel
    private lateinit var newsAdapter:ArticleAdapter
    var TAG ="SearchFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
        newsAdapter.onSaveItemClickListener {
            viewModel.insertArticles(it)
            Snackbar.make(requireView(),"Saved", Snackbar.LENGTH_SHORT).show()
        }
        newsAdapter.onDeleteItemClickListener {
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(),"Removed", Snackbar.LENGTH_SHORT).show()
        }
        newsAdapter.onShareItemClickListener {
            shareNews(context,it)
        }

        var searchJob: Job?=null
        binding.etSearch.addTextChangedListener {editable->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(constants.SEARCH_TIME_DELAY)
                editable?.let {
                    if(it.toString().trim().isNotEmpty()){
                        viewModel.getSearchNews(it.toString())
                }
            }


            }
        }
        viewModel.searchNews.observe(viewLifecycleOwner, Observer {newsResponse->
            when(newsResponse){
                is Resource.Success->{
                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE
                    newsResponse.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                    //binding.rvSearchNewsFragment.visibility = View.VISIBLE
                }
                is Resource.Error->{
                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE
                    newsResponse.message?.let {
                        Log.i(TAG,it)
                    }
                }
                is Resource.Loading->{
                    binding.shimmerFrameLayout.startShimmer()
                }
            }
        })
    }

    private fun setViewModelObserver() {
        TODO("Not yet implemented")
    }

    private fun setUpRecyclerView() {
       newsAdapter = ArticleAdapter()
       binding.rvSearchNewsFragment.apply {
           adapter = newsAdapter
           layoutManager = LinearLayoutManager(activity)
       }
    }

}