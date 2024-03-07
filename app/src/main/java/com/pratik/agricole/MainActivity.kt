package com.pratik.agricole

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    val homeFragment : Home = Home()
    val feilds : Feilds = Feilds()
    val tasks : Tasks = Tasks()
    val profile : Profile = Profile()
    val fragmentlist = arrayOf(homeFragment,tasks,feilds,profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,homeFragment).commit()
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(fragmentlist[0])
                R.id.task -> replaceFragment(fragmentlist[1])
                R.id.farm -> replaceFragment(fragmentlist[2])
                R.id.profile -> replaceFragment(fragmentlist[3])
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fragment).commit()

    }
}