package uk.co.cerihughes.mgm.android.ui.albumscores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.databinding.AlbumScoresListItemBinding
import uk.co.cerihughes.mgm.android.ui.isSpotifyInstalled
import uk.co.cerihughes.mgm.android.ui.launchSpotify

class AlbumScoresAdapter(private val viewModel: AlbumScoresViewModel) :
    RecyclerView.Adapter<AlbumScoresAdapter.AlbumScoresItemViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AlbumScoresItemViewHolder {
        val itemBinding = AlbumScoresListItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        val holder = AlbumScoresItemViewHolder(viewModel, itemBinding)
        itemBinding.root.setOnClickListener(holder)
        return holder
    }

    override fun onBindViewHolder(holder: AlbumScoresItemViewHolder, index: Int) {
        val albumScoreViewModel = viewModel.scoreViewModel(index) ?: return
        holder.bind(albumScoreViewModel)
    }

    override fun getItemCount() = viewModel.numberOfScores()

    class AlbumScoresItemViewHolder(
        private val viewModel: AlbumScoresViewModel,
        private val itemBinding: AlbumScoresListItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(viewModel: AlbumScoreViewModel) {
            val largestDimension =
                itemView.resources.getDimension(R.dimen.album_scores_list_item_height)
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

            itemBinding.albumNameTV.text = viewModel.albumName()
            itemBinding.artistNameTV.text = viewModel.artistName()
            itemBinding.ratingTV.text = viewModel.rating()
            itemBinding.ratingTV.setTextColor(viewModel.ratingColour())
            itemBinding.positionTV.text = viewModel.position()

            itemBinding.awardIV.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    viewModel.awardImage(),
                    null
                )
            )
        }

        override fun onClick(v: View?) {
            var view = v ?: return
            var context = view.context

            if (context.packageManager.isSpotifyInstalled() == false) {
                return
            }

            val albumScoreViewModel = viewModel.scoreViewModel(adapterPosition) ?: return
            var spotifyURL = albumScoreViewModel.spotifyURL() ?: return

            val intent = Intent(Intent.ACTION_VIEW)
            intent.launchSpotify(context, spotifyURL)
        }
    }
}
