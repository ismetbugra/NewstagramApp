package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.Article
import com.example.newsapp.data.viewmodels.NewsViewModel
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.ui.adapters.BreakingNewsAdapter
import com.example.newsapp.ui.adapters.SearchNewsAdapter
import com.example.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var searchingNewsAdapter:SearchNewsAdapter
    private lateinit var newsList: List<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchNewsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                searchNews(p0)
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                searchNews(p0)
                return true
            }

        })

        //breaking news fragmenttakiyle aynı işlemler
        viewModel.searchNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success ->{
                    hideProgressbar()
                    response.data?.let {
                        setupRecyclerView(response.data.articles)
                    }
                }
                is Resource.Error ->{
                    hideProgressbar()
                    response.message?.let {
                        println(it)
                    }
                }
                is Resource.Loading ->{
                    showProgressbar()
                }
            }

        })
    }

    private fun setupRecyclerView(newsList: List<Article>){
        searchingNewsAdapter= SearchNewsAdapter(requireContext(),newsList)
        binding.rvSearchNews.adapter=searchingNewsAdapter
        binding.rvSearchNews.layoutManager= LinearLayoutManager(requireContext())
    }

    private fun hideProgressbar(){
        binding.paginationProgressBar.visibility=View.INVISIBLE
    }

    private fun showProgressbar(){
        binding.paginationProgressBar.visibility=View.VISIBLE
    }

    fun searchNews(query:String){
        viewModel.searchNews(query)
    }


}