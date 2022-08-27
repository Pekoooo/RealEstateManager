package com.example.masterdetailflowkotlintest.ui.addProperty

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentAddPropertyBinding
import com.example.masterdetailflowkotlintest.databinding.FragmentItemListBinding
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.placeholder.PlaceholderContent
import com.example.masterdetailflowkotlintest.ui.list.PropertyListFragment
import com.example.masterdetailflowkotlintest.ui.list.PropertyListViewModel
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddPropertyFragment : Fragment() {

    private val viewModel: AddPropertyViewModel by viewModels()
    private val housingType: MutableList<String> = ArrayList()
    private var _binding: FragmentAddPropertyBinding? = null
    private val binding: FragmentAddPropertyBinding get() = _binding!!

    companion object {
        const val TAG = "MyAddPropertyFragment"
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).supportActionBar?.title = "New Property"
        _binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateHousingTypeList()
        setUpSpinner()
        createToolbar()

        if(arguments?.containsKey(ARG_ITEM_ID) == true){
            val id: Int = requireArguments().getInt(ARG_ITEM_ID)
            retrieveData(id)
        }

    }

    private fun retrieveData(id: Int) {
        //getPropertyInfo


        //fill up edit text fields in view

    }

    private fun setViewInfo(it: Property) {
        (binding.spinnerEditText as TextView).text = it.type
        (binding.propertyDescriptionEditText as TextView).text = it.description
    }

    private fun createToolbar() {

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_add_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {

                R.id.save -> {
                    Log.d(TAG, "onMenuItemSelected: ${binding.propertyDescriptionEditText.text}")


                    if(allFieldsAreFilled()){
                        Toast.makeText(context, "New property saved", Toast.LENGTH_LONG).show()
                        viewModel.createProperty(getPropertyInfo())
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(context, "Make sure all fields are filled", Toast.LENGTH_SHORT).show()
                    }

                    true
                }

                else -> false
            }
        }, viewLifecycleOwner)
    }

    private fun allFieldsAreFilled(): Boolean {

        return binding.agentNameEditText.text.toString() != "" &&
                binding.propertyDescriptionEditText.text.toString() != "" &&
                binding.surfaceEditText.text.toString() != "" &&
                binding.addressEditText.text.toString() != "" &&
                binding.roomsEditText.text.toString() != "" &&
                binding.cityEditText.text.toString() != "" &&
                binding.bedroomEditText.text.toString() != "" &&
                binding.postalCodeEditText.text.toString() != "" &&
                binding.bathroomEditText.text.toString() != "" &&
                binding.countryEditText.text.toString() != "" &&
                binding.neighborhoodEditText.text.toString() != "" &&
                binding.priceEditText.text.toString() != ""
    }

    private fun getPropertyInfo(): Property {

        return Property(
            0,
            binding.surfaceEditText.text.toString(),
            binding.spinner.selectedItem.toString(),
            binding.addressEditText.text.toString(),
            binding.cityEditText.text.toString(),
            binding.neighborhoodEditText.text.toString(),
            binding.postalCodeEditText.text.toString(),
            binding.countryEditText.text.toString(),
            binding.priceEditText.text.toString(),
            binding.bathroomEditText.text.toString(),
            binding.bedroomEditText.text.toString(),
            Calendar.getInstance().time.toString(),
            binding.roomsEditText.text.toString(),
            binding.propertyDescriptionEditText.text.toString(),
            binding.agentNameEditText.text.toString()
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        Log.d(MainActivity.TAG, "onViewStateRestored: ")

    }

    private fun populateHousingTypeList() {
        housingType.add("House")
        housingType.add("Penthouse")
        housingType.add("Flat")
        housingType.add("Mansion")
    }



    private fun setUpSpinner() {
        val spinner = binding.spinner
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            housingType
        )

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

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(MainActivity.TAG, "onDestroyView: ")
        _binding = null
    }


}