package com.example.locationsharingapp_dipti_ict_amad_l4_04_11

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.Adapter_dipti_ict_amad_l4_04_11.UserAdapter_dipti_ict_amad_l4_04_11
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.databinding.FragmentFriendsDiptiIctAmadL40411Binding
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.viewmodel_dipti_ict_amad_l4_04_11.AuthenticationViewModel_dipti_ict_amad_l4_04_11
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.viewmodel_dipti_ict_amad_l4_04_11.FirestoreViewModel_dipti_ict_amad_l4_04_11
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.viewmodel_dipti_ict_amad_l4_04_11.LocationViewModel_dipti_ict_amad_l4_04_11
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FriendsFragment_dipti_ict_amad_l4_04_11 : Fragment() {
    private lateinit var binding: FriendsFragment_dipti_ict_amad_l4_04_11
    private lateinit var firestoreViewModel: FirestoreViewModel_dipti_ict_amad_l4_04_11
    private lateinit var authenticationViewModel: AuthenticationViewModel_dipti_ict_amad_l4_04_11
    private lateinit var userAdapter: UserAdapter_dipti_ict_amad_l4_04_11
    private lateinit var locationViewModel: LocationViewModel_dipti_ict_amad_l4_04_11
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Location Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFriendsDiptiIctAmadL40411Binding.inflate(inflater,container,false)

        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel_dipti_ict_amad_l4_04_11::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel_dipti_ict_amad_l4_04_11::class.java)
        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel_dipti_ict_amad_l4_04_11::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationViewModel.initializeFusedLocationClient(fusedLocationClient)

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Permission is already granted
            getLocation()
        }

        userAdapter = UserAdapter_dipti_ict_amad_l4_04_11(emptyList())
        binding.userRV.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        fetchUsers()

        binding.locationBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MapsActivity_dipti_ict_amad_l4_04_11::class.java))
        }

        return binding.root
    }

    private fun fetchUsers() {
        firestoreViewModel.getAllUsers(requireContext()) {
            userAdapter.updateData(it)
        }
    }

    private fun getLocation() {
        locationViewModel.getLastLocation {
            // Save location to Firestore for the current user
            authenticationViewModel.getCurrentUserId().let { userId ->
                firestoreViewModel.updateUserLocation(requireContext(), userId, it)
            }
        }
    }
}
