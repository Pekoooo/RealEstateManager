package com.example.masterdetailflowkotlintest.ui.list

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentItemListBinding
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.Constants.ARG_NO_ITEM_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyListFragment : Fragment(R.layout.fragment_item_list) {

    private val viewModel: PropertyListViewModel by viewModels()
    private var _binding: FragmentItemListBinding? = null
    private var allProperties: List<Property> = listOf()
    private val binding get() = _binding!!
    private var currencySwitch = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as MainActivity).supportActionBar?.title = "List"

        _binding = FragmentItemListBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList

        viewModel.isDollar.observe(viewLifecycleOwner) {


            currencySwitch = when (it) {
                true -> true
                false -> false
            }

            setupRecyclerView(recyclerView, currencySwitch)


        }



        binding.addPropertyTabletFab?.setOnClickListener {
            val action =
                PropertyListFragmentDirections.actionItemListFragmentToAddPropertyFragment(
                    ARG_NO_ITEM_ID
                )
            findNavController().navigate(action)
        }

        binding.addPropertyPhoneFab?.setOnClickListener {
            val action =
                PropertyListFragmentDirections.actionItemListFragmentToAddPropertyFragment(
                    ARG_NO_ITEM_ID
                )
            findNavController().navigate(action)
        }

        viewModel.allProperties.observe(viewLifecycleOwner) {
            allProperties = it
            setupRecyclerView(recyclerView, currencySwitch)
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_main_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {

                R.id.map -> {
                    findNavController().navigate(R.id.mapFragment)
                    true
                }

                R.id.currency -> {

                    viewModel.switchCurrencyUi()

                    true
                }

                else -> false
            }
        }, viewLifecycleOwner)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        currencyState: Boolean
    ) {
        recyclerView.adapter = PropertyListAdapter(
            allProperties,
            currencyState
        ) {

            val action =
                PropertyListFragmentDirections.showItemDetail(it!!.id)

            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}