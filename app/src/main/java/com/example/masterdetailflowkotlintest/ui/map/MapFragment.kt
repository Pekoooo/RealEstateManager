package com.example.masterdetailflowkotlintest.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.ui.main.MainActivity


class MapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).supportActionBar?.title = "Map"

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

}