package com.example.newsapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.Article
import com.example.newsapp.databinding.BreakingNewsRowBinding
import com.example.newsapp.ui.fragments.BreakingNewsFragmentDirections

class BreakingNewsAdapter(var mContext:Context,var newsList:List<Article>): RecyclerView.Adapter<BreakingNewsAdapter.NewsVH>() {
    class NewsVH(var binding: BreakingNewsRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        return NewsVH(BreakingNewsRowBinding.inflate(LayoutInflater.from(mContext),parent,false))
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        val t=holder.binding
        val article=newsList[position]
        t.tvDescription.text=article.description
        t.tvPublishedAt.text=article.publishedAt
        t.tvTitle.text=article.title
        t.tvSource.text= article.source.name
        Glide.with(mContext).load(article.urlToImage).placeholder(R.drawable.loading_iamge)
            .into(t.ivArticleImage)

        t.newsConstraintlayout.setOnClickListener {
            val action=BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(article)
            Navigation.findNavController(it).navigate(action)
        }
    }
}