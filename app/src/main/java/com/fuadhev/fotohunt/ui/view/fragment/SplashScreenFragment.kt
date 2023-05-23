package com.fuadhev.fotohunt.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fuadhev.fotohunt.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())
        },2000)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}