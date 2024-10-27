package com.example.locationsharingapp_dipti_ict_amad_l4_04_11

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.databinding.FragmentProfileDiptiIctAmadL40411Binding
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.viewmodel_dipti_ict_amad_l4_04_11.AuthenticationViewModel_dipti_ict_amad_l4_04_11
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.viewmodel_dipti_ict_amad_l4_04_11.FirestoreViewModel_dipti_ict_amad_l4_04_11
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.viewmodel_dipti_ict_amad_l4_04_11.LocationViewModel_dipti_ict_amad_l4_04_11
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment_dipti_ict_amad_l4_04_11 : Fragment() {

    private lateinit var binding: FragmentProfileDiptiIctAmadL40411Binding
    private lateinit var authViewModel: AuthenticationViewModel_dipti_ict_amad_l4_04_11
    private lateinit var firestoreViewModel: FirestoreViewModel_dipti_ict_amad_l4_04_11
    private lateinit var locationViewModel: LocationViewModel_dipti_ict_amad_l4_04_11
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDiptiIctAmadL40411Binding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this).get(AuthenticationViewModel_dipti_ict_amad_l4_04_11::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel_dipti_ict_amad_l4_04_11::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel_dipti_ict_amad_l4_04_11::class.java)

        binding.logoutBtn150.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity_dipti_ict_amad_l4_04_11::class.java))
        }
        binding.homeBtn150.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity_dipti_ict_amad_l4_04_11::class.java))
        }
        loadUserInfo()

        binding.updateBtn150.setOnClickListener {
            val newName = binding.nameEt0.text.toString()
            val newLocation = binding.locationEt0.text.toString()

            updateProfile(newName, newLocation)
        }

        return binding.root
    }

    private fun updateProfile(newName: String, newLocation: String) {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.uid
            firestoreViewModel.updateUser(requireContext(), userId, newName, newLocation)
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity_dipti_ict_amad_l4_04_11::class.java))
        } else {
            // Handle the case where the current user is null
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserInfo() {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            binding.emailEt.setText(currentUser.email)

            firestoreViewModel.getUser(requireContext(), currentUser.uid) {
                if (it != null) {
                    binding.nameEt0.setText(it.displayName)

                    firestoreViewModel.getUserLocation(requireContext(), currentUser.uid) {
                        if (it.isNotEmpty()) {
                            binding.locationEt0.setText(it)
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }
}
