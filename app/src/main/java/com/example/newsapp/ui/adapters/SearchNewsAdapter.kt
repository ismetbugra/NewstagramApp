package com.example.newsapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.Article
import com.example.newsapp.databinding.SearchNewsRowBinding
import com.example.newsapp.ui.fragments.SearchNewsFragmentDirections

class SearchNewsAdapter(var mContext: Context,var searchList:List<Article>): RecyclerView.Adapter<SearchNewsAdapter.SearchVH>() {
    class SearchVH(var binding: SearchNewsRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        return SearchVH(SearchNewsRowBinding.inflate(LayoutInflater.from(mContext),parent,false))
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        val t=holder.binding
        val article=searchList[position]
        t.tvDescription.text=article.description
        t.tvPublishedAt.text=article.publishedAt
        t.tvTitle.text=article.title
        t.tvSource.text= article.source.name
        Glide.with(mContext).load(article.urlToImage).into(t.ivArticleImage)

        t.cardViewSearch.setOnClickListener {
            val action=SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(article)
            Navigation.findNavController(t.ivArticleImage).navigate(action)
        }
    }
}