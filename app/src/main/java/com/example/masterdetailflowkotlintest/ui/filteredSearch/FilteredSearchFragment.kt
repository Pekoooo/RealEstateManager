package com.example.masterdetailflowkotlintest.ui.filteredSearch

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentFilterdSearchBinding
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.model.pojo.SearchResultObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilteredSearchFragment : Fragment() {

    private var _binding: FragmentFilterdSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilteredSearchViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterdSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClick()
        filteredList()
    }

    private fun setupOnClick() {
        binding.btnSearch.setOnClickListener {
            collectDataToSearchFun(
                if (binding.autoCompleteTextViewTypeS.text.toString() != "") binding.autoCompleteTextViewTypeS.text.toString() else null,
                if (binding.autoCompleteTextViewCityS.text.toString() != "") binding.autoCompleteTextViewCityS.toString() else null,
                if (binding.autoCompleteTextViewNeiS.text.toString() != "") binding.autoCompleteTextViewNeiS.text.toString() else null,
                binding.switch4.isChecked,
                binding.switch5.isChecked,
                if (binding.tiedPriceFrom.text.toString() != "") binding.tiedPriceFrom.text.toString()
                    .toInt() else null,
                if (binding.tiedPriceUpTo.text.toString() != "") binding.tiedPriceUpTo.text.toString()
                    .toInt() else null,
                if (binding.tiedSizeFrom.text.toString() != "") binding.tiedSizeFrom.text.toString()
                    .toInt() else null,
                if (binding.tiedSizeTo.text.toString() != "") binding.tiedSizeTo.text.toString()
                    .toInt() else null,
                binding.toggleNbPicture.isChecked
            )
        }
    }

    private fun collectDataToSearchFun(
        type: String?,
        city: String?,
        neighbourhood: String?,
        soldLast3Month: Boolean,
        addedLess7Days: Boolean,
        startingPrice: Int?,
        priceLimit: Int?,
        sizeFrom: Int?,
        sizeUpTo: Int?,
        numberOfPhoto: Boolean
    ) {
        viewModel.searchUserCriteria(
            type,
            city,
            neighbourhood,
            soldLast3Month,
            addedLess7Days,
            startingPrice,
            priceLimit,
            sizeFrom,
            sizeUpTo,
            numberOfPhoto
        )
    }

    private fun filteredList() {
        viewModel.filteredList.observe(requireActivity()) {
            Log.e(ContentValues.TAG, "filteredList: ${it.size}")
            btnSeeResultsVisible(it.size, it)
        }
    }

    private fun btnSeeResultsVisible(result: Int, list: List<Property>) {
        binding.btnSeeResult.visibility = View.VISIBLE
        val searchResults = SearchResultObject(list)
        binding.btnSeeResult.setOnClickListener {
            val action =
                FilteredSearchFragmentDirections.actionFilteredSearchFragmentToSearchResult(searchResults)
            findNavController().navigate(action)
        Log.e(ContentValues.TAG, "btnSeeResultsVisible: here")
        /*if (result == 0) {
            binding.btnSeeResult.visibility = View.INVISIBLE
        } else {

            }*/
        }
    }


}