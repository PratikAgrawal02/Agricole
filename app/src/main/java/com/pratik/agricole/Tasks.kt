package com.pratik.agricole

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pratik.agricole.databinding.FragmentFeildsBinding
import com.pratik.agricole.databinding.FragmentTasksBinding


class Tasks : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentTasksBinding.inflate(inflater, container, false)

        return binding.root
    }
}