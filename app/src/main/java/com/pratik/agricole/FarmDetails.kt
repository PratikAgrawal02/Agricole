package com.pratik.agricole

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pratik.agricole.models.FarmModel

class FarmDetails : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_details)
        val farmnumber = intent.getStringExtra("farmnumber").toString()
        auth = FirebaseAuth.getInstance()
        val ref: DatabaseReference = database.reference.child("users").child(auth.uid.toString()).child("farms").child(farmnumber)
        ref.get().addOnSuccessListener {

        }
    }
}