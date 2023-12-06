package uk.co.cerihughes.mgm.android.ui.albumscores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import uk.co.cerihughes.mgm.android.databinding.FragmentAlbumScoresBinding
import uk.co.cerihughes.mgm.android.ui.RemoteDataLoadingViewModel

class AlbumScoresFragment : Fragment() {

    private lateinit var binding: FragmentAlbumScoresBinding
    private val viewModel: AlbumScoresViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumScoresBinding.inflate(layoutInflater)

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.adapter = AlbumScoresAdapter(viewModel)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.isLoaded) {
            return
        }

        binding.progressLoader.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadData()
            binding.progressLoader.visibility = View.GONE
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}
