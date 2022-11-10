package com.example.masterdetailflowkotlintest.ui.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentItemListBinding
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.ui.add.AddPropertyFragment
import com.example.masterdetailflowkotlintest.ui.detail.PropertyDetailFragment
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.Constants.ARG_NO_ITEM_ID
import com.example.masterdetailflowkotlintest.utils.CurrencyType
import com.example.masterdetailflowkotlintest.utils.DefineScreenSize.Companion.isTablet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyListFragment : Fragment(R.layout.fragment_item_list) {

    private val viewModel: PropertyListViewModel by viewModels()
    private var _binding: FragmentItemListBinding? = null
    private var allProperties: List<Property> = listOf()
    private val binding get() = _binding!!
    private lateinit var currencyType: CurrencyType


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

        viewModel.currencyType.observe(viewLifecycleOwner) {

            currencyType = when (it) {
                CurrencyType.DOLLAR, null -> CurrencyType.DOLLAR
                CurrencyType.EURO -> CurrencyType.EURO
            }

            setupRecyclerView(recyclerView, currencyType)

        }

        binding.addPropertyTabletFab?.setOnClickListener {

            requireActivity().supportFragmentManager.commit {
                    replace(
                        R.id.item_detail_frame_layout,
                        AddPropertyFragment.newInstance(ARG_NO_ITEM_ID)
                    )


            }

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
            setupRecyclerView(recyclerView, currencyType)

            

        }

        initMenuItems()


    }

    private fun initMenuItems() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_main_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {

                R.id.mapFragment -> {
                    findNavController().navigate(R.id.mapFragment)
                    true
                }

                R.id.currency -> {

                    viewModel.switchCurrencyUi()

                    true
                }

                R.id.search ->  {

                    val action =
                        PropertyListFragmentDirections.actionItemListFragmentToFilteredSearchFragment()
                    findNavController().navigate(action)

                    true
                }

                else -> false
            }
        }, viewLifecycleOwner)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        currencyType: CurrencyType
    ) {
        recyclerView.adapter = PropertyListAdapter(
            allProperties,
            currencyType
        ) {

            when (isTablet(requireContext())) {

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
                        it?.let { it1 -> PropertyListFragmentDirections.showItemDetail(it1.id) }

                    if (action != null) {
                        findNavController().navigate(action)
                    }

                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}