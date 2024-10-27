package com.example.locationsharingapp_dipti_ict_amad_l4_04_11

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.databinding.ActivityMainDiptiIctAmadL40411Binding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity_dipti_ict_amad_l4_04_11 : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainDiptiIctAmadL40411Binding.inflate(layoutInflater)
    }
    lateinit var actionDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomBar.setupWithNavController(navController)
        binding.drawerNav.setupWithNavController(navController)

        actionDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawerlayout,
            R.string.nav_open,
            R.string.nav_close
        )
        actionDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.drawerNav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    Firebase.auth.signOut()
                    startActivity(
                        Intent(this, LoginActivity_dipti_ict_amad_l4_04_11::class.java)
                    )
                    finish()
                }
                R.id.profileFragment2 -> {
                    navController.navigate(R.id.profileFragment2)
                }
                R.id.friendsFragment3 -> {
                    navController.navigate(R.id.friendsFragment3)
                }
            }
            true
        }

        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    Firebase.auth.signOut()
                    startActivity(
                        Intent(this, LoginActivity_dipti_ict_amad_l4_04_11::class.java)
                    )
                    finish()
                }
                R.id.friendsFragment3 -> {
                    navController.navigate(R.id.friendsFragment3)
                }
                R.id.profileFragment2 -> {
                    navController.navigate(R.id.profileFragment2)
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}

