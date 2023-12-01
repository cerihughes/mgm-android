package uk.co.cerihughes.mgm.android.ui.latestevent

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
import uk.co.cerihughes.mgm.android.databinding.FragmentLatestEventBinding
import uk.co.cerihughes.mgm.android.databinding.LatestEventLocationListItemBinding
import uk.co.cerihughes.mgm.android.ui.RemoteDataLoadingViewModel

class LatestEventFragment : Fragment() {

    private lateinit var binding: FragmentLatestEventBinding
    val viewModel: LatestEventViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_latest_event, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLatestEventBinding.inflate(layoutInflater)

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.adapter = LatestEventAdapter(viewModel)
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
