package com.example.capstone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.ListAdapter
import com.example.capstone.LoadingHelper
import com.example.capstone.databinding.ListHistoryBinding
import com.example.capstone.viewmodel.HistoryViewModel
import com.github.ybq.android.spinkit.style.FadingCircle

class HistoryFragment : Fragment() {
    private var _binding: ListHistoryBinding? = null
    private val binding get() = _binding

    private val viewModel: HistoryViewModel by activityViewModels()
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListAdapter()
        binding?.listUserRecycler?.layoutManager = LinearLayoutManager(requireContext())
        binding?.listUserRecycler?.adapter = adapter

        val progressBar: ProgressBar = binding?.spinKit as ProgressBar
        val fadeCircle = FadingCircle()
        progressBar.indeterminateDrawable = fadeCircle

        viewModel.getData()
        viewModel.data.observe(viewLifecycleOwner, { data ->
            adapter.data = data
            LoadingHelper.toggleLoading(binding?.spinKit, binding?.listUserRecycler, false)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) LoadingHelper.toggleLoading(
                binding?.spinKit,
                binding?.listUserRecycler,
                true
            )
        })
    }
}