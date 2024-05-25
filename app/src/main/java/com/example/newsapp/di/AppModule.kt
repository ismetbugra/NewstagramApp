package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.datasource.NewsDataSource
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.retrofit.ApiUtils
import com.example.newsapp.retrofit.NewsApi
import com.example.newsapp.room.ArticleDao
import com.example.newsapp.room.DatabaseArticle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNewsRepository(nds:NewsDataSource):NewsRepository{
        return NewsRepository(nds)
    }
    @Provides
    @Singleton
    fun provideNewsDataSource(newsDao:NewsApi,articleDao: ArticleDao):NewsDataSource{
        return NewsDataSource(newsDao,articleDao)
    }

    @Provides
    @Singleton
    fun provideNewsDao():NewsApi{
        return ApiUtils.getNewsApi()
    }

    @Provides
    @Singleton
    fun providesArticleDao(@ApplicationContext context: Context):ArticleDao{
        return Room.databaseBuilder(context.applicationContext,DatabaseArticle::class.java,
            "article_db.sqlite").build().getArticleDao()
    }


}