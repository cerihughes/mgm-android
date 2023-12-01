package uk.co.cerihughes.mgm.android.ui.albumscores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.databinding.FragmentAlbumScoresBinding
import uk.co.cerihughes.mgm.android.ui.RemoteDataLoadingViewModel

class AlbumScoresFragment : Fragment() {

    private lateinit var binding: FragmentAlbumScoresBinding
    val viewModel: AlbumScoresViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album_scores, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAlbumScoresBinding.inflate(layoutInflater)

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.adapter = AlbumScoresAdapter(viewModel)
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.isLoaded()) {
            return
        }

        binding.progressLoader.visibility = View.VISIBLE

        viewModel.loadData(object : RemoteDataLoadingViewModel.LoadDataCallback {
            override fun onDataLoaded() {
                binding.progressLoader.visibility = View.GONE
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        })
    }
}
