package com.application.newsappgolapbarman.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.newsappgolapbarman.MainActivity
import com.application.newsappgolapbarman.R
import com.application.newsappgolapbarman.adapter.SavedArticleAdapter
import com.application.newsappgolapbarman.databinding.FragmentSavedragmentBinding
import com.application.newsappgolapbarman.utils.shareNews
import com.application.newsappgolapbarman.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedFragment : Fragment(R.layout.fragment_savedragment) {
    lateinit var viewModel:NewsViewModel
    lateinit var newsAdapter:SavedArticleAdapter
    lateinit var binding:FragmentSavedragmentBinding
    var TAG ="SavedFragment"


    fun setUpRecyclerView(){
        newsAdapter = SavedArticleAdapter()
        binding.rvSavedNewsFragment.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.onShareItemClickListener {
            shareNews(context,it)
        }

        val onItemTouchHelperCallback = object :  ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article =newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(requireView(),"Deleted Successfully",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.insertArticles(article)
                    }
                    show()
                }

            }
        }
        ItemTouchHelper(onItemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNewsFragment)
        }
    }


    private fun setViewModelObserver() {
        viewModel = (activity as MainActivity).viewModel

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            Log.i(TAG," ${it.size} ")
            newsAdapter.differ.submitList(it)
            binding.rvSavedNewsFragment.visibility = View.VISIBLE
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.visibility = View.GONE
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
        setViewModelObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
}