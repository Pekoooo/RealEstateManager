package com.example.masterdetailflowkotlintest.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.placeholder.PlaceholderContent;
import com.example.masterdetailflowkotlintest.databinding.FragmentItemListBinding
import com.example.masterdetailflowkotlintest.ui.addProperty.AddPropertyActivity


class PropertyListFragment : Fragment() {


    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

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
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}