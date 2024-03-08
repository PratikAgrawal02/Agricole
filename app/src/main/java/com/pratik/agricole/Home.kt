package com.pratik.agricole

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pratik.agricole.databinding.FragmentHomeBinding


class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentHomeBinding.inflate(inflater, container, false)
        binding.chatgptcard.setOnClickListener {
            openchatgpt(it)
        }
        return binding.root
    }

    fun openchatgpt(view: View) {
        startActivity(Intent(requireActivity(), ChatGPT::class.java))

    }
}