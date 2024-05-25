package com.example.newsapp.data.datasource

import com.example.newsapp.data.Article
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.retrofit.NewsApi
import com.example.newsapp.room.ArticleDao
import com.example.newsapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NewsDataSource @Inject constructor(var newsDao:NewsApi,var articleDao: ArticleDao) {

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int):Response<NewsResponse> =
        withContext(Dispatchers.IO){
            return@withContext newsDao.getBreakingNews(countryCode,pageNumber)
        }

    suspend fun searchNews(searchQuery:String,pageNumber:Int):Response<NewsResponse> =
        withContext(Dispatchers.IO){
            return@withContext newsDao.searchForNews(searchQuery,pageNumber)
        }

    suspend fun upsert(article:Article){
        articleDao.upsert(article)
    }

    suspend fun getSavedNews():List<Article> =
        withContext(Dispatchers.IO){
            return@withContext articleDao.getAllArticles()
        }

    suspend fun deleteArticles(article:Article){
        articleDao.deleteArticle(article)
    }

}