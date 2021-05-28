package com.example.capstone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstone.databinding.SignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: SignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpBinding.inflate(inflater, container, false)
        return binding.root
    }
}