package com.example.newsapp.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.Article
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(var nrepo:NewsRepository):ViewModel() {

    val breakingNews=MutableLiveData<Resource<NewsResponse>>()
    var breakingNewsPage=1

    val searchNews=MutableLiveData<Resource<NewsResponse>>()
    var searchNewsPage=1

    var savedNews=MutableLiveData<List<Article>>()

    init {
        getBreakinNews(Constants.country)
        getSavedNews()
    }

    // breaking newsleri çekme
    fun getBreakinNews(countryCode:String)=
        CoroutineScope(Dispatchers.Main).launch {
            breakingNews.value=Resource.Loading()
            val response=nrepo.getBreakingNews(countryCode,breakingNewsPage)
            breakingNews.value=handleBreakingNewsResponse(response)
        }

    //searchfragment arama çekme fonku
    fun searchNews(searchQuery:String) =
        CoroutineScope(Dispatchers.Main).launch {
            searchNews.value=Resource.Loading()
            val response=nrepo.searchNews(searchQuery,searchNewsPage)
            searchNews.value=handleSearchNewsResponse(response)
        }

    // dönen responseları kontrol handle etme işlenmi
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article:Article){
       CoroutineScope(Dispatchers.Main).launch {
           nrepo.upsert(article)
       }
    }

    fun getSavedNews(){
        CoroutineScope(Dispatchers.Main).launch{
            savedNews.value= nrepo.getSavedNews()
        }
    }

    fun deleteNews(article:Article){
        CoroutineScope(Dispatchers.Main).launch {
            nrepo.deleteArticles(article)
            getSavedNews()
        }
    }
}