package com.nda.blum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.nda.blum.interfaces.IBackToHub


class MainActivity : AppCompatActivity(), IBackToHub {

    var bottomNavigationView: BottomNavigationView? = null
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener(this,
                OnSuccessListener<InstanceIdResult> { instanceIdResult ->
                    val token = instanceIdResult.token
                    println("TOKEN: $token")
                })

        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
       setUpNavigation()
    }

    fun setUpNavigation() {
        bottomNavigationView = findViewById(R.id.bttm_nav)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        NavigationUI.setupWithNavController(
            bottomNavigationView!!,
            navHostFragment!!.navController
        )
    }

    override fun backToHubFragment() {
        navController.navigate(R.id.hubAlumnoFragment)
    }

}
