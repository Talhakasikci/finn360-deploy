package com.talhakasikci.finn360fe.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.databinding.ActivityMainBinding
import com.talhakasikci.finn360fe.utils.TokenManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Token Kontrolü
        val tokenManager = TokenManager(this)
        val token = tokenManager.getToken()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.navigation) // Graph dosyanın adı nav_graph ise

        if (token != null) {
            navGraph.setStartDestination(R.id.main_fragment)
        } else {
            navGraph.setStartDestination(R.id.signInFragment)
        }

        navController.graph = navGraph

        // Splash'i kapat
        splashScreen.setKeepOnScreenCondition { false }

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment,
                R.id.registerFragment -> {
                    binding.bottomNavigationMenu.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationMenu.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {

        binding.bottomNavigationMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_fragment -> {
                    navController.navigate(R.id.main_fragment)
                    true
                }
                R.id.portfolio_fragment -> {
                    navController.navigate(R.id.portfolio_fragment)
                    true
                }
                R.id.articleFragment -> {
                    navController.navigate(R.id.articleFragment)
                    true
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}