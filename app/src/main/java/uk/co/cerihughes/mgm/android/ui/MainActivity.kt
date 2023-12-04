package uk.co.cerihughes.mgm.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.databinding.ActivityMainBinding
import uk.co.cerihughes.mgm.android.ui.albumscores.AlbumScoresFragment
import uk.co.cerihughes.mgm.android.ui.latestevent.LatestEventFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        loadFragment(viewModel.selectedItemId)

        binding.navigation.setOnItemSelectedListener {
            loadFragment(it.itemId)
        }
    }

    private fun loadFragment(itemId: Int): Boolean {
        viewModel.selectedItemId = itemId

        when (itemId) {
            R.id.navigation_latest_event -> {
                loadFragment(LatestEventFragment())
                return true
            }
            R.id.navigation_album_scores -> {
                loadFragment(AlbumScoresFragment())
                return true
            }
        }
        return false
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
