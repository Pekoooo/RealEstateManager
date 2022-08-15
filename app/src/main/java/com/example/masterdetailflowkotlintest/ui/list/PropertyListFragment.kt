package com.example.masterdetailflowkotlintest.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.placeholder.PlaceholderContent;
import com.example.masterdetailflowkotlintest.databinding.FragmentItemListBinding
import com.example.masterdetailflowkotlintest.ui.addProperty.AddPropertyActivity
import com.example.masterdetailflowkotlintest.ui.detail.PropertyDetailFragment


class PropertyListFragment : Fragment() {


    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "MyPropertyListFragment"
        private const val ARG_ITEM_ID = "item_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (context as AppCompatActivity).supportActionBar!!.title = "Properties"

        val recyclerView: RecyclerView = binding.itemList
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)



        setupRecyclerView(recyclerView, itemDetailFragmentContainer)

        binding.addPropertyTabletFab?.setOnClickListener {
            val intent = Intent(context, AddPropertyActivity::class.java)
            startActivity(intent)
        }

        binding.addPropertyPhoneFab?.setOnClickListener {
            val intent = Intent(context, AddPropertyActivity::class.java)
            startActivity(intent)
        }


    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        itemDetailFragmentContainer: View?
    ) {
        recyclerView.adapter = PropertyAdapter(
            PlaceholderContent.ITEMS, itemDetailFragmentContainer
        ) {

            val args = Bundle()
            args.putString(
                ARG_ITEM_ID, it!!.id.toString()
            )

            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, args)
            } else {
                findNavController().navigate(R.id.show_item_detail, args)
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}