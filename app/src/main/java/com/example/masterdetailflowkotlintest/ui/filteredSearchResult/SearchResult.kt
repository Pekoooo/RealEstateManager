package com.example.masterdetailflowkotlintest.ui.filteredSearchResult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentSearchResultBinding
import com.example.masterdetailflowkotlintest.ui.detail.PropertyDetailFragment
import com.example.masterdetailflowkotlintest.ui.list.PropertyListAdapter
import com.example.masterdetailflowkotlintest.ui.list.PropertyListFragmentDirections
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.DefineScreenSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResult : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val args: SearchResultArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList


        setupRecyclerView(recyclerView)

    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
    ) {

        val filteredProperties = args.resultList.result
        recyclerView.adapter = PropertyListAdapter(
            filteredProperties
        ) {

            when (DefineScreenSize.isTablet(requireContext())) {

                true -> {
                    Log.d(MainActivity.TAG, "setupRecyclerView: replaced called in property list fragment")

                    requireActivity().supportFragmentManager.commit {
                        if (it != null) {
                            replace(
                                R.id.item_detail_frame_layout,
                                PropertyDetailFragment.newInstance(it.id)
                            )
                        }

                    }

                }

                else -> {

                    val action =
                        it?.let { it1 -> SearchResultDirections.actionSearchResultToItemDetailFragment(it1.id) }

                    if (action != null) {
                        findNavController().navigate(action)
                    }

                }
            }

        }
    }



}