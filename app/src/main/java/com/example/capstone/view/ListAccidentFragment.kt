package com.example.capstone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.ListAdapter
import com.example.capstone.databinding.ListAccidentBinding
import com.example.capstone.model.AccidentDetail
import com.example.capstone.model.AccidentParcelable
import com.example.capstone.viewmodel.ListAccidentViewModel
import com.github.ybq.android.spinkit.style.FadingCircle

class ListAccidentFragment : Fragment() {
    private var _binding: ListAccidentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListAccidentViewModel by activityViewModels()
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListAccidentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListAdapter()
        binding.listUserRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.listUserRecycler.adapter = adapter

        val progressBar: ProgressBar = binding?.spinKit as ProgressBar
        val fadeCircle = FadingCircle()
        progressBar.indeterminateDrawable = fadeCircle

        adapter.setAcceptedClick(object : ListAdapter.IOnBtnClick {
            override fun onBtnClicK(data: AccidentDetail) {
                viewModel.setAccepted(data.accidentId)
            }
        })

        adapter.setItemClick(object : ListAdapter.IOnItemClick {
            override fun onItemClick(data: AccidentDetail) {
                val sentData =
                    AccidentParcelable(
                        data.user,
                        data.phone,
                        data.address,
                        data.photo,
                        data.coordinate.latitude,
                        data.coordinate.longitude
                    )
                val toDetail =
                    ListAccidentFragmentDirections.actionListAccidentFragmentToDetailFragment(
                        sentData
                    )

                binding.root.findNavController().navigate(toDetail)
            }
        })

        viewModel.getData()
        viewModel.data.observe(viewLifecycleOwner, { data ->
            adapter.data = data
            toggleLoading(false)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) toggleLoading(true)
        })
    }

    private fun toggleLoading(isLoading: Boolean) {
        if (!isLoading) {
            binding.spinKit.visibility = View.GONE
            binding.listUserRecycler.visibility = View.VISIBLE
        } else {
            binding.spinKit.visibility = View.VISIBLE
            binding.listUserRecycler.visibility = View.GONE
        }
    }
}