package com.example.nitrixintership.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nitrixintership.R
import com.example.nitrixintership.adapters.MoviesListAdapter
import com.example.nitrixintership.databinding.FragmentMovieBinding
import com.example.nitrixintership.utills.observeOnce
import com.example.nitrixintership.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { MoviesListAdapter() }
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        readDatabase()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readMovies.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("fragment", "readDatabase called")
                    mAdapter.setData(database[0].movieResult)
                } else {
                    requestApiData()
                }

            }
        }
    }

    private fun requestApiData() {
        mainViewModel.getMovies()
        mainViewModel.movieGetResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                mAdapter.setData(response)
            } else {
                loadDataFromCache()
            }

        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readMovies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].movieResult)
                }
            }
        }
    }
}