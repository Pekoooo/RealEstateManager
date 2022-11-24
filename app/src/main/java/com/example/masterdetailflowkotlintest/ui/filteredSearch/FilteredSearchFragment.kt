package com.example.masterdetailflowkotlintest.ui.filteredSearch

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentFilterdSearchBinding
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.model.pojo.SearchResultObject
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.DefineScreenSize.Companion.isTablet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilteredSearchFragment : Fragment() {

    private var _binding: FragmentFilterdSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilteredSearchViewModel by viewModels()
    private var listProperties: List<Property>? = listOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterdSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Filtered Search"
        getListProperties()
        setupOnClick()
        filteredList()
    }

    private fun setupOnClick() {
        binding.btnSearch.setOnClickListener {

            collectDataToSearchFun(
                if (binding.autoCompleteTextViewTypeS.text.toString() != "") binding.autoCompleteTextViewTypeS.text.toString() else null,
                if (binding.autoCompleteTextViewCityS.text.toString() != "") binding.autoCompleteTextViewCityS.text.toString() else null,
                if (binding.autoCompleteTextViewNeiS.text.toString() != "") binding.autoCompleteTextViewNeiS.text.toString() else null,
                if (binding.tiedPriceFrom.text.toString() != "") binding.tiedPriceFrom.text.toString()
                    .toInt() else null,
                if (binding.tiedPriceUpTo.text.toString() != "") binding.tiedPriceUpTo.text.toString()
                    .toInt() else null,
                if (binding.tiedSizeFrom.text.toString() != "") binding.tiedSizeFrom.text.toString()
                    .toInt() else null,
                if (binding.tiedSizeTo.text.toString() != "") binding.tiedSizeTo.text.toString()
                    .toInt() else null
            )
        }
    }

    private fun collectDataToSearchFun(
        type: String?,
        city: String?,
        neighbourhood: String?,
        startingPrice: Int?,
        priceLimit: Int?,
        sizeFrom: Int?,
        sizeUpTo: Int?
    ) {
        viewModel.searchUserCriteria(
            type,
            city,
            neighbourhood,
            startingPrice,
            priceLimit,
            sizeFrom,
            sizeUpTo
        )
    }

    private fun filteredList() {
        viewModel.filteredList.observe(requireActivity()) {
            btnSeeResultsVisible(it.size, it)
        }
    }

    private fun getListProperties() {
        viewModel.getPropertyList()
        viewModel.allProperties.observe(requireActivity()) {
            listProperties = it
        }
    }

    private fun btnSeeResultsVisible(result: Int, list: List<Property>) {

        when (result) {

            0 -> {
                Toast.makeText(requireContext(), "No properties found", Toast.LENGTH_SHORT).show()
                binding.btnSeeResult.visibility = View.INVISIBLE
            }

            else -> {
                binding.btnSeeResult.visibility = View.VISIBLE
                val resultButtonText = "See Results ($result)"
                binding.btnSeeResult.text = resultButtonText
                binding.btnSeeResult.setOnClickListener {
                    val searchResults = SearchResultObject(list)
                    val action =
                        FilteredSearchFragmentDirections.actionFilteredSearchFragmentToSearchResult(
                            searchResults
                        )
                    findNavController().navigate(action)
                }

            }

        }



    }


}