package com.pratik.agricole

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.pratik.agricole.databinding.FragmentHomeBinding
import java.util.*


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
        loadLocate()
        binding.changelang.setOnClickListener{

            showChangeLanguageDialog(requireActivity());
        }
        return binding.root
    }

    fun openchatgpt(view: View) {
        startActivity(Intent(requireActivity(), ChatGPT::class.java))

    }
    private fun showChangeLanguageDialog(requireActivity: FragmentActivity) {
        val listitems = arrayOf("हिंदी","తెలుగు","English")

        val mBuilder = AlertDialog.Builder(requireActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listitems, -1) { dialog, which ->
            if (which == 0) {
                setLocate("hi")
                requireActivity.recreate()
            } else if (which == 1) {
                setLocate("te")
                requireActivity.recreate()
            } else if (which == 2) {
                setLocate("en")
                requireActivity.recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()



    }

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        requireActivity().baseContext.resources.updateConfiguration(config, requireActivity().baseContext.resources.displayMetrics)

        val editor = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }
    private fun loadLocate() {
        val sharedPreferences = requireActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocate(language)
        }
    }




}
