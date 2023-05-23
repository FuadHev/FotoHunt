package com.fuadhev.fotohunt.ui.view.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fuadhev.fotohunt.R
import com.fuadhev.fotohunt.common.Resource
import com.fuadhev.fotohunt.databinding.FragmentHomeBinding
import com.fuadhev.fotohunt.model.Hit
import com.fuadhev.fotohunt.ui.adapter.ImageClickListener
import com.fuadhev.fotohunt.ui.adapter.SearchFotoAdapter
import com.fuadhev.fotohunt.ui.view.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var autoAdapterItems: ArrayAdapter<String>
    private lateinit var searchText: String
    private val viewModel by viewModels<HomeViewModel>()
    private val fotoAdapter by lazy {
        SearchFotoAdapter(object : ImageClickListener {
            override fun imageClickListener(imageInfo: Hit) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToFotoDetailFragment(
                        imageInfo
                    )
                )
            }
        }, emptyList())
    }
    private val autocomletedItems = arrayListOf("All", "Photo", "Illustration", "Vector")
    private var image_type = "all"
    private var page = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setSearchViewListener()
        setAutoCompletedTypes()
        setPreviosAndNext()



        viewModel.fotosMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = VISIBLE
                    binding.linearLayout2.visibility = GONE
                }
                is Resource.Success -> {
                    it.data?.let { data ->
                        binding.progressBar.visibility = GONE
                        if (data.isNotEmpty()) {
                            binding.lottieAnim.visibility = GONE
                            fotoAdapter.updateFotos(data)
                            binding.linearLayout2.visibility = VISIBLE
                            if (page == 1) {
                                binding.previos.visibility = INVISIBLE
                            } else {
                                binding.previos.visibility = VISIBLE
                            }

                        } else {
                            fotoAdapter.updateFotos(emptyList())
                            binding.lottieAnim.setAnimation(R.raw.notfound)
                            binding.lottieAnim.visibility = VISIBLE

                        }
                    }
                }
                is Resource.Error -> {
                    binding.linearLayout2.visibility = GONE
                    binding.progressBar.visibility = GONE
                    binding.lottieAnim.visibility = VISIBLE
                    binding.lottieAnim.setAnimation(R.raw.notfound)
                }
            }

        }

    }


    private fun setAutoCompletedTypes() {
        autoAdapterItems =
            ArrayAdapter(requireContext(), R.layout.auto_complete_txt_item, autocomletedItems)
        binding.autoCompleteText.setAdapter(autoAdapterItems)
        binding.autoCompleteText.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            image_type = selectedItem
//            Toast.makeText(requireContext(), image_type, Toast.LENGTH_SHORT).show()

        }
    }


    private fun setSearchViewListener() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (query.trim() != "") {
                    binding.searchView.clearFocus()
                    Log.e("image typ", "$query $image_type")
                    viewModel.searchFotos(query, image_type.lowercase())
                    page = 1
                    searchText = query
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                if (newText.trim()==""){
//                    fotoAdapter.updateFotos(emptyList())
//                    binding.lottieAnim.setAnimation(R.raw.detective_search)
//                    binding.lottieAnim.visibility= VISIBLE
//                    binding.linearLayout2.visibility=GONE
//                }
                return false
            }


        })
    }

    private fun setPreviosAndNext() {
        binding.previos.setOnClickListener {
            page -= 1
            viewModel.searchFotos(searchText, image_type, page)
        }
        binding.next.setOnClickListener {

            page += 1
            viewModel.searchFotos(searchText, image_type, page)

        }

    }

    private fun setRecyclerView() {
        binding.lottieAnim.setAnimation(R.raw.detective_search)
        binding.lottieAnim.visibility = VISIBLE
        binding.linearLayout2.visibility = GONE
        binding.searchRv.setHasFixedSize(true)
        binding.searchRv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRv.adapter = fotoAdapter
    }

}