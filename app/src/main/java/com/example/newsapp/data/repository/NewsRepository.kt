package com.example.newsapp.data.repository

import com.example.newsapp.data.Article
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.data.datasource.NewsDataSource
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(var nds:NewsDataSource) {

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int): Response<NewsResponse> = nds.getBreakingNews(countryCode,pageNumber)
    suspend fun searchNews(searchQuery:String,pageNumber:Int):Response<NewsResponse> = nds.searchNews(searchQuery,pageNumber)
    suspend fun upsert(article: Article) = nds .upsert(article)
    suspend fun getSavedNews():List<Article> = nds.getSavedNews()
    suspend fun deleteArticles(article:Article) = nds.deleteArticles(article)
}