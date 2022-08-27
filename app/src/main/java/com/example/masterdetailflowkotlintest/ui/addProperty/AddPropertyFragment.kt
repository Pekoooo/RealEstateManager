package com.example.masterdetailflowkotlintest.ui.addProperty

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.util.*


@AndroidEntryPoint
class AddPropertyFragment : Fragment() {

    private val viewModel: AddPropertyViewModel by viewModels()
    private val housingType: MutableList<String> = ArrayList()
    private var _binding: FragmentAddPropertyBinding? = null
    private val binding: FragmentAddPropertyBinding get() = _binding!!
    private var currentId: Int? = null

    companion object {
        const val TAG = "MyAddPropertyFragment"
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateHousingTypeList()
        setUpSpinner()
        createToolbar()

        if(argsHaveKey()){
            (activity as MainActivity).supportActionBar?.title = "Update Property"
            currentId = requireArguments().getInt(ARG_ITEM_ID)
            retrieveData(currentId!!)
        } else {
            (activity as MainActivity).supportActionBar?.title = "New Property"
        }
    }

    private fun argsHaveKey(): Boolean = arguments?.containsKey(ARG_ITEM_ID) == true

    private fun retrieveData(id: Int) {
        lifecycle.coroutineScope.launch{
            viewModel.getPropertyById(id).collect{ displayData(it) }
        }
    }

    private fun displayData(property: Property){
        (binding.spinnerEditText as TextView).text = property.type // does not work
        (binding.agentNameEditText as TextView).text = property.agent
        (binding.propertyDescriptionEditText as TextView).text = property.description
        (binding.surfaceEditText as TextView).text = property.surface
        (binding.addressEditText as TextView).text = property.address
        (binding.roomsEditText as TextView).text = property.rooms
        (binding.cityEditText as TextView).text = property.city
        (binding.bedroomEditText as TextView).text = property.bedrooms
        (binding.postalCodeEditText as TextView).text = property.postalCode
        (binding.bathroomEditText as TextView).text = property.bathrooms
        (binding.countryEditText as TextView).text = property.country
        (binding.neighborhoodEditText as TextView).text = property.neighborhood
        (binding.priceEditText as TextView).text = property.price

        //Todo : Add POIs
    }

    private fun createToolbar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_add_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {

                R.id.save -> {
                    if(allFieldsAreFilled()){
                        if(argsHaveKey()) {
                            Toast.makeText(context, "Property Updated", Toast.LENGTH_LONG).show()
                            Log.d(MainActivity.TAG, "onMenuItemSelected: ${getPropertyInfo().description}")
                            val property = getPropertyInfo()
                            property.id = currentId!!
                            viewModel.updateProperty(property)
                            findNavController().navigateUp()
                        } else {
                            Toast.makeText(context, "New property saved", Toast.LENGTH_LONG).show()
                            viewModel.createProperty(getPropertyInfo())
                            findNavController().navigateUp()
                        }
                    } else {
                        Toast.makeText(context, "Make sure all fields are filled", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner)
    }

    private fun allFieldsAreFilled(): Boolean =
                binding.agentNameEditText.text.toString() != "" &&
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


    private fun getPropertyInfo() =
         Property(
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
        _binding = null
    }


}