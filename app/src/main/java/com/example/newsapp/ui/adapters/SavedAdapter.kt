package com.example.newsapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.Article
import com.example.newsapp.data.viewmodels.NewsViewModel
import com.example.newsapp.databinding.ArticleRecyclerRowBinding

class SavedAdapter(var mContext:Context,var savedList:List<Article>,var viewModel:NewsViewModel): RecyclerView.Adapter<SavedAdapter.SavedVH>() {
    class SavedVH(var binding: ArticleRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedVH {
        return SavedVH(ArticleRecyclerRowBinding.inflate(LayoutInflater.from(mContext),parent,false))
    }

    override fun getItemCount(): Int {
        return savedList.size
    }

    override fun onBindViewHolder(holder: SavedVH, position: Int) {
        val t=holder.binding
        val article=savedList[position]
        t.tvDescription.text=article.description
        t.tvPublishedAt.text=article.publishedAt
        t.tvTitle.text=article.title
        t.tvSource.text= article.source.name
        Glide.with(mContext).load(article.urlToImage).into(t.ivArticleImage)

        t.deleteCardView.setOnClickListener {
            viewModel.deleteNews(article)
        }
    }
}