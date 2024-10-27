package com.example.locationsharingapp_dipti_ict_amad_l4_04_11.viewmodel_dipti_ict_amad_l4_04_11

import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCompleteListener

class LocationViewModel_dipti_ict_amad_l4_04_11 : ViewModel() {
    private var fusedLocationClient: FusedLocationProviderClient? = null

    // Method to get the last known location
    fun getLastLocation(callback: (String) -> Unit) {
        fusedLocationClient?.lastLocation
            ?.addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val lastLocation = task.result
                    val latitude = lastLocation.latitude
                    val longitude = lastLocation.longitude
                    val location = "Lat: $latitude, Long: $longitude"
                    callback(location)
                } else {
                    // Handle failure or missing permissions
                    callback("Location not available")
                }
            })
    }

    // Method to initialize fusedLocationClient
    fun initializeFusedLocationClient(client: FusedLocationProviderClient) {
        fusedLocationClient = client
    }
}
