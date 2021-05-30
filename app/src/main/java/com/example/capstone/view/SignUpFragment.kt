package com.example.capstone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.capstone.databinding.SignUpBinding
import com.example.capstone.helper.ValidationHelper
import com.example.capstone.viewmodel.SignUpViewModel
import com.github.ybq.android.spinkit.style.FadingCircle

class SignUpFragment : Fragment() {
    private var _binding: SignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = binding.spinKit
        val fadeCircle = FadingCircle()
        progressBar.indeterminateDrawable = fadeCircle

        binding.btnNsignup.setOnClickListener {
            if (!validation()) {
                viewModel.register(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etUsername.text.toString(),
                    binding.etPhonenumber.text.toString().toInt(),
                    requireContext()
                )
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                binding.spinKit.visibility = View.VISIBLE
                binding.btnNsignup.isClickable = false
            } else {
                binding.spinKit.visibility = View.GONE
                binding.btnNsignup.isClickable = true
            }
        })

        viewModel.isSuccess.observe(viewLifecycleOwner, { isSuccess ->
            if (isSuccess) {
                binding.root.findNavController()
                    .navigate(SignUpFragmentDirections.actionSignUpFragmentToListAccidentFragment())
            }
        })
    }

    private fun validation(): Boolean =
        ValidationHelper.checkEmpty(binding.etUsername) ||
                ValidationHelper.checkEmpty(binding.etEmail) ||
                ValidationHelper.checkEmpty(binding.etPhonenumber) ||
                ValidationHelper.checkMinimum(binding.etPassword, 6) ||
                ValidationHelper.checkSame(binding.etPassword, binding.etRwpassword)
}