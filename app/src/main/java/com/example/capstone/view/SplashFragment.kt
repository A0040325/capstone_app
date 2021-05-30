package com.example.capstone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.capstone.databinding.SplashScreenBinding
import com.example.capstone.viewmodel.SplashViewModel
import com.github.ybq.android.spinkit.style.FadingCircle

class SplashFragment : Fragment() {
    private var _binding: SplashScreenBinding? = null
    private val binding get() = _binding

    private val viewModel: SplashViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = binding?.spinKit as ProgressBar
        val fadeCircle = FadingCircle()
        progressBar.indeterminateDrawable = fadeCircle

        viewModel.getData()
        viewModel.isLogin.observe(viewLifecycleOwner, { data ->
            if (data) {
                binding?.root?.findNavController()
                    ?.navigate(SplashFragmentDirections.actionSplashFragmentToListAccidentFragment())
            } else {
                binding?.root?.findNavController()
                    ?.navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
            }
        })
    }
}