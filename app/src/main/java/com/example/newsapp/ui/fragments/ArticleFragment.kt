package com.example.newsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.data.viewmodels.NewsViewModel
import com.example.newsapp.databinding.FragmentArticleBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {
    private lateinit var binding:FragmentArticleBinding
    private val args by navArgs<ArticleFragmentArgs>()
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentArticleBinding.inflate(layoutInflater,container,false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article=args.article

        binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
            settings.javaScriptEnabled=true
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(it,"Article is saved",Snackbar.LENGTH_SHORT).show()
        }

        // haber urli diğer uygulamalara gönderme işlemi
        binding.fabShare.setOnClickListener {
            val intent=Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,article.url.toString())
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share to:"))
        }
    }


}