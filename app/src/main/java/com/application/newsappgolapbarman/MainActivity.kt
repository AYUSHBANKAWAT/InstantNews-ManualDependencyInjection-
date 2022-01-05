package com.application.newsappgolapbarman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.application.newsappgolapbarman.databinding.ActivityMainBinding
import com.application.newsappgolapbarman.fragments.BreakingNewsFragment
import com.application.newsappgolapbarman.fragments.SavedFragment
import com.application.newsappgolapbarman.fragments.SearchFragment
import com.application.newsappgolapbarman.repository.NewsRepository
import com.application.newsappgolapbarman.repository.db.ArticleDatabase
import com.application.newsappgolapbarman.viewModel.NewsViewModel
import com.application.newsappgolapbarman.viewModel.NewsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProvider)[NewsViewModel::class.java]
        binding.bottomNavigationView.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        loadFragment(BreakingNewsFragment())
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        val fragment:Fragment
        when(it.itemId){
            R.id.breakingNews->{
                fragment = BreakingNewsFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.searchNews->{
                fragment = SearchFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bookMark->{
                fragment = SavedFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}