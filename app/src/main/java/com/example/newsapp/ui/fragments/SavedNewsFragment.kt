package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.Article
import com.example.newsapp.data.viewmodels.NewsViewModel
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.ui.adapters.SavedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {
    private lateinit var binding:FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var savedList:List<Article>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSavedNewsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel.getSavedNews()

        viewModel.savedNews.observe(viewLifecycleOwner, Observer {
            binding.rvBreakingNews.adapter=SavedAdapter(requireContext(),it,viewModel)
            binding.rvBreakingNews.layoutManager=LinearLayoutManager(requireContext())
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSavedNews()
    }


}