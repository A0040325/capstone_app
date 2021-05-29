package com.example.capstone.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.databinding.DetailAccidentBinding
import com.example.capstone.model.AccidentParcelable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailFragment : Fragment(), OnMapReadyCallback {
    private var _binding: DetailAccidentBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var data: AccidentParcelable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailAccidentBinding.inflate(inflater, container, false)

        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.detail_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = args.data
        val image = Base64.decode(data.photo, Base64.DEFAULT)
        binding.detailAlamat.text = data.address
        binding.detailName.text = data.user
        binding.detailTelp.text = data.phone.toString()
        binding.detailCoord.text = getString(R.string.coordinate, data.latitude, data.longitude)

        Glide.with(requireContext())
            .asBitmap()
            .load(image)
            .into(binding.detailImage)

        binding.callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${data.phone}")
            startActivity(intent)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        val coord = LatLng(data.latitude, data.longitude)

        map.addMarker(
            MarkerOptions()
                .position(coord)
                .title("Kekerasan Terdeteksi")
        )

        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(coord, 10F)
        )
    }
}