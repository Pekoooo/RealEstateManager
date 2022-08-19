package com.example.masterdetailflowkotlintest.ui.addProperty

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.ActivityAddPropertyBinding
import com.example.masterdetailflowkotlintest.databinding.FragmentAddPropertyBinding
import com.example.masterdetailflowkotlintest.databinding.FragmentItemListBinding
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.ui.list.PropertyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class AddPropertyFragment : Fragment() {

    private val viewModel: AddPropertyViewModel by viewModels()
    private val housingType: MutableList<String> = ArrayList()
    private lateinit var binding: FragmentAddPropertyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateHousingTypeList()
        setUpSpinner()
    }

    private fun populateHousingTypeList() {
        housingType.add("House")
        housingType.add("Penthouse")
        housingType.add("Flat")
        housingType.add("Mansion")
    }

    private fun setUpSpinner() {
        val spinner = binding.spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, housingType)

        spinner.adapter = adapter

        binding.spinnerEditText.setOnClickListener {
            binding.spinner.performClick()
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                (binding.spinnerEditText as TextView).text = binding.spinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

}