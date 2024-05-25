package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.Article
import com.example.newsapp.data.viewmodels.NewsViewModel
import com.example.newsapp.databinding.BreakingNewsRowBinding
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.ui.adapters.BreakingNewsAdapter
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var breakingNewsAdapter: BreakingNewsAdapter
    private lateinit var newsList: List<Article>
    private var countries=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBreakingNewsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countries.add("Türkiye")
        countries.add("America")
        countries.add("England")


        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success ->{
                    hideProgressbar()
                    response.data?.let {
                        setupRecyclerView(response.data.articles)
                    }
                }
                is Resource.Error ->{
                    hideProgressbar()
                    response.message?.let {
                        println(it)
                    }
                }
                is Resource.Loading ->{
                    showProgressbar()
                }
            }

        })

        binding.locationButton.setOnClickListener {
            var customLayout=LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog,null)
            
            val alertDialog=AlertDialog.Builder(requireContext())
            alertDialog.setView(customLayout)
            //spinner customlayoutta bulundu
            var spinner=customLayout.findViewById<Spinner>(R.id.spinner)

            var arrayAdapter=
                ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,countries)

            spinner.setAdapter(arrayAdapter)
            spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
                    //spinnerdan bir veri seçildiğinde seçilenin indexine göre Constantstaki country değişecek böylece arama lokasyonu değişcek
                    when(index){
                         0 ->{
                            Constants.country="tr"
                        }
                        1->{
                            Constants.country="us"
                        }
                        2->{
                            Constants.country="gb"
                        }
                    }
                    viewModel.getBreakinNews(Constants.country)

                    Toast.makeText(requireContext(),"News location was changed to ${countries[index]}",Toast.LENGTH_LONG).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
            alertDialog.create().show()
        }

    }

    private fun setupRecyclerView(newsList: List<Article>){
        breakingNewsAdapter= BreakingNewsAdapter(requireContext(),newsList)
        binding.rvBreakingNews.adapter=breakingNewsAdapter
        binding.rvBreakingNews.layoutManager=LinearLayoutManager(requireContext())
    }

    private fun hideProgressbar(){
        binding.paginationProgressBar.visibility=View.INVISIBLE
    }

    private fun showProgressbar(){
        binding.paginationProgressBar.visibility=View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBreakinNews(Constants.country)
    }

}