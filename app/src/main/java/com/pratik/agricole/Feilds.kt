package com.pratik.agricole

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pratik.agricole.databinding.FragmentFeildsBinding
import com.pratik.agricole.databinding.FragmentProfileBinding
import com.pratik.agricole.models.FarmAdapter
import com.pratik.agricole.models.FarmModel
import java.util.ArrayList

class Feilds : Fragment() {


    private var _binding: FragmentFeildsBinding? = null
    private val binding get() = _binding!!
    var farmlist: ArrayList<FarmModel> = ArrayList()
    lateinit var adapter: FarmAdapter
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentFeildsBinding.inflate(inflater, container, false)

        binding.vediorv.layoutManager = LinearLayoutManager(requireActivity())
        adapter = FarmAdapter(requireActivity(),farmlist)
        binding.vediorv.adapter = adapter
        database =  FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val ref: DatabaseReference = database.reference.child("users").child(auth.uid.toString()).child("farms")
        ref.get().addOnSuccessListener {
            farmlist.clear()
            for (item in it.children){
                val farmModel = FarmModel(
                    item.child("farmname").value.toString(),
                    item.child("farmsize").value.toString(),
                    item.key ,
                    item.child("farmimage").value.toString()
                )
                farmlist.add(farmModel)
            }

            adapter.notifyDataSetChanged()
        }
        binding.addfarm.setOnClickListener{
            startActivity(Intent(requireActivity(),Addfarm::class.java))
        }


        return binding.root
    }

}