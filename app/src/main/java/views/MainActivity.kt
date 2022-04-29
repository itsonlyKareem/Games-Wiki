package views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.omega.gamestar.R
import fragments.GamesFragment
import fragments.PlatformsFragment

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getViews()

        // Nav Settings.
        loadFragment(GamesFragment())
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.optionsFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView.setupWithNavController(navController)
        bottomNavView.setOnClickListener { println("clicked") }
        bottomNavView.setOnItemSelectedListener {
            val fragment: Fragment
            when (it.itemId) {
                R.id.gamesFragment -> {
                    fragment = GamesFragment()
                    loadFragment(fragment)
                    true
                }

                R.id.platformsFragment -> {
                    fragment = PlatformsFragment()
                    loadFragment(fragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun getViews() {
        bottomNavView = findViewById(R.id.bottomNav)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.optionsFragment, fragment).commit()
    }



}