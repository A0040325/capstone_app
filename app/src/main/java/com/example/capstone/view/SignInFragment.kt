package com.example.capstone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.capstone.databinding.SignInBinding
import com.example.capstone.viewmodel.SignInViewModel
import com.github.ybq.android.spinkit.style.FadingCircle

class SignInFragment : Fragment() {
    private var _binding: SignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = binding.spinKit
        val fadeCircle = FadingCircle()
        progressBar.indeterminateDrawable = fadeCircle

        binding.btnSignin.setOnClickListener {
            if (binding.etPassword.text.toString() == "") {
                binding.etPassword.error = "harus diisi!"
                return@setOnClickListener
            } else if (binding.etUsername.text.toString() == "") {
                binding.etUsername.error = "harus diisi!"
                return@setOnClickListener
            }

            viewModel.login(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString(),
                requireContext()
            )
        }

        binding.btnSignup.setOnClickListener {
            binding.root.findNavController()
                .navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                binding.spinKit.visibility = View.VISIBLE
                binding.btnSignin.isClickable = false
                binding.btnSignup.isClickable = false
            } else {
                binding.spinKit.visibility = View.GONE
                binding.btnSignin.isClickable = true
                binding.btnSignup.isClickable = true
            }
        })

        viewModel.isSuccess.observe(viewLifecycleOwner, { isSuccess ->
            if (isSuccess) {
                binding.root.findNavController()
                    .navigate(SignInFragmentDirections.actionSignInFragmentToListAccidentFragment())
            }
        })
    }
}