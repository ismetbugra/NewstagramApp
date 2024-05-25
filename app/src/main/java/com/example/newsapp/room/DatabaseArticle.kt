package com.example.newsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class DatabaseArticle:RoomDatabase() {
    abstract fun getArticleDao():ArticleDao
}