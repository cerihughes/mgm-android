package uk.co.cerihughes.mgm.android.ui.latestevent

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.databinding.LatestEventEntityListItemBinding
import uk.co.cerihughes.mgm.android.databinding.LatestEventLocationListItemBinding
import uk.co.cerihughes.mgm.android.databinding.LatestEventTitleListItemBinding
import uk.co.cerihughes.mgm.android.ui.BlurTransformation
import uk.co.cerihughes.mgm.android.ui.isSpotifyInstalled
import uk.co.cerihughes.mgm.android.ui.launchSpotify

class LatestEventAdapter(private val viewModel: LatestEventViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return viewModel.itemType(position).rawValue
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            LatestEventViewModel.ItemType.TITLE.rawValue -> {
                val itemBinding = LatestEventTitleListItemBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return LatestEventTitleItemViewHolder(itemBinding)
            }

            LatestEventViewModel.ItemType.LOCATION.rawValue -> {
                val itemBinding = LatestEventLocationListItemBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return LatestEventLocationItemViewHolder(itemBinding)
            }

            else -> {
                val itemBinding = LatestEventEntityListItemBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                val holder = LatestEventEntityItemViewHolder(viewModel, itemBinding)
                itemBinding.root.setOnClickListener(holder)
                return holder
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (viewModel.itemType(position)) {
            LatestEventViewModel.ItemType.TITLE -> {
                holder as? LatestEventTitleItemViewHolder ?: return
                val title = viewModel.headerTitle(position) ?: return
                holder.bind(title)
            }

            LatestEventViewModel.ItemType.LOCATION -> {
                holder as? LatestEventLocationItemViewHolder ?: return
                holder.bind(viewModel)
            }

            LatestEventViewModel.ItemType.ENTITY -> {
                holder as? LatestEventEntityItemViewHolder ?: return
                val eventEntityViewModel = viewModel.eventEntityViewModel(position) ?: return
                holder.bind(eventEntityViewModel)
            }
        }
    }

    override fun getItemCount(): Int {
        return viewModel.numberOfItems()
    }

    class LatestEventTitleItemViewHolder(private val itemBinding: LatestEventTitleListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(title: String) {
            itemBinding.textView.text = title
        }
    }

    class LatestEventLocationItemViewHolder(private val itemBinding: LatestEventLocationListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(viewModel: LatestEventViewModel) {
            viewModel.mapReference()?.let {
                val position = LatLng(it.first, it.second)
                itemBinding.mapView.onCreate(null)
                itemBinding.mapView.getMapAsync {
                    it.uiSettings.setAllGesturesEnabled(false)
                    val marker = it.addMarker(
                        MarkerOptions().position(position).title(viewModel.locationName())
                    )
                    it.moveCamera(CameraUpdateFactory.newLatLng(position))
                    marker?.showInfoWindow()
                }
                itemBinding.mapView.onResume()
            }
        }
    }

    class LatestEventEntityItemViewHolder(
        private val viewModel: LatestEventViewModel,
        private val itemBinding: LatestEventEntityListItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(viewModel: LatestEventEntityViewModel) {
            val largestDimension =
                itemView.resources.getDimension(R.dimen.latest_event_entity_list_item_height)
            viewModel.coverArtURL(largestDimension.toInt())?.let {
                Picasso.get()
                    .load(it)
                    .placeholder(R.drawable.album1)
                    .into(itemBinding.coverArtIV)
            } ?: itemBinding.coverArtIV.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.album1,
                    null
                )
            )

            viewModel.coverArtURL(largestDimension.toInt())?.let {
                Picasso.get()
                    .load(it)
                    .transform(BlurTransformation(itemView.context))
                    .into(itemBinding.backgroundIV)
            }

            itemBinding.entityTypeTV.text = viewModel.entityType
            itemBinding.albumNameTV.text = viewModel.entityName
            itemBinding.artistNameTV.text = viewModel.entityOwner
        }

        override fun onClick(v: View?) {
            var view = v ?: return
            var context = view.context

            if (context.packageManager.isSpotifyInstalled() == false) {
                return
            }

            val eventEntityViewModel = viewModel.eventEntityViewModel(adapterPosition) ?: return
            var spotifyURL = eventEntityViewModel.spotifyURL ?: return

            val intent = Intent(Intent.ACTION_VIEW)
            intent.launchSpotify(context, spotifyURL)
        }
    }
}
