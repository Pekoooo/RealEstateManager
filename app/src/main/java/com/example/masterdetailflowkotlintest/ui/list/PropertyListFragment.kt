package com.example.masterdetailflowkotlintest.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.placeholder.PlaceholderContent;
import com.example.masterdetailflowkotlintest.databinding.FragmentItemListBinding
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyListFragment : Fragment(R.layout.fragment_item_list) {

    private val viewModel: PropertyListViewModel by viewModels()
    private var _binding: FragmentItemListBinding? = null
    private var allProperties: List<Property> = listOf()
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "MyPropertyListFragment"
        private const val ARG_ITEM_ID = "item_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(MainActivity.TAG, "onCreateView: ")
        (activity as MainActivity).supportActionBar?.title = "List"

        _binding = FragmentItemListBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(MainActivity.TAG, "onViewCreated: ")

        val recyclerView: RecyclerView = binding.itemList
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        binding.addPropertyTabletFab?.setOnClickListener {
            findNavController().navigate(R.id.addPropertyFragment)
        }

        binding.addPropertyPhoneFab?.setOnClickListener {
            findNavController().navigate(R.id.addPropertyFragment)
        }

        viewModel.allProperties.observe(viewLifecycleOwner){
            allProperties = it
            setupRecyclerView(recyclerView, itemDetailFragmentContainer)
        }

        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_main_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean
            = when (menuItem.itemId){

                R.id.map -> {
                    findNavController().navigate(R.id.mapFragment)
                    true
                }

                R.id.currency -> {
                    Toast.makeText(context, "currency changed", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        },viewLifecycleOwner)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        itemDetailFragmentContainer: View?
    ) {
        recyclerView.adapter = PropertyAdapter(
            allProperties,
            itemDetailFragmentContainer
        ) {

            val args = Bundle()
            args.putInt(
                ARG_ITEM_ID, it!!.id
            )

            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, args)
            } else {
                findNavController().navigate(R.id.show_item_detail, args)
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        Log.d(MainActivity.TAG, "onViewStateRestored: ")

    }

    override fun onDestroyView() {

        super.onDestroyView()
        Log.d(MainActivity.TAG, "onDestroyView: ")
        _binding = null
    }
}