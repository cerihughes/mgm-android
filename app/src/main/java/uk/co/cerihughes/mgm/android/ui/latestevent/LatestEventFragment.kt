package uk.co.cerihughes.mgm.android.ui.latestevent

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
import uk.co.cerihughes.mgm.android.databinding.FragmentLatestEventBinding
import uk.co.cerihughes.mgm.android.ui.RemoteDataLoadingViewModel

class LatestEventFragment : Fragment() {

    private lateinit var binding: FragmentLatestEventBinding
    private val viewModel: LatestEventViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLatestEventBinding.inflate(layoutInflater)

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.adapter = LatestEventAdapter(viewModel)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.isLoaded()) {
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
