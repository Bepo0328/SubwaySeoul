package kr.co.bepo.subway_seoul.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import kr.co.bepo.subway_seoul.R
import kr.co.bepo.subway_seoul.databinding.ActivityMainBinding
import kr.co.bepo.subway_seoul.extensions.toGone
import kr.co.bepo.subway_seoul.extensions.toVisible
import kr.co.bepo.subway_seoul.presentation.stationarrivals.StationArrivalsFragmentArgs

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolBar)
        setupActionBarWithNavController(navigationController)
    }

    private fun bindViews() {
        navigationController.addOnDestinationChangedListener { _, destination, argument ->
            if (destination.id == R.id.stations_arrivals_dest) {
                title = StationArrivalsFragmentArgs.fromBundle(argument!!).station.name
                binding.toolBar.toVisible()
            } else {
                binding.toolBar.toGone()
            }
        }
    }
}
