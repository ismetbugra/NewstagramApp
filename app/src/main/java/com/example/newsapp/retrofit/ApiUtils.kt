package com.example.newsapp.retrofit

import com.example.newsapp.util.Constants.Companion.BASE_URL

class ApiUtils {
    companion object{
        fun getNewsApi():NewsApi{
            return RetrofitClient.getClient(BASE_URL).create(NewsApi::class.java)
        }
    }
}