package com.talhakasikci.finn360fe.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.talhakasikci.finn360fe.adapter.StockMarketCVAdapter.*
import com.talhakasikci.finn360fe.databinding.InsturmentRowItemBinding


import com.talhakasikci.finn360fe.models.watchlist.WatchList

class WatchListAdapter(private val onStockClick: (WatchList) -> Unit): ListAdapter<WatchList, WatchListAdapter.WatchListViewHolder>(InstrumentDiffCallback()) {
    var shouldAnimateBackground = false


    class InstrumentDiffCallback : DiffUtil.ItemCallback<WatchList> () {
        override fun areItemsTheSame(oldItem: WatchList, newItem: WatchList): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: WatchList, newItem: WatchList): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: WatchList, newItem: WatchList): Any? {
            if (oldItem.priceChangePercent != newItem.priceChangePercent || oldItem.currentPrice != newItem.currentPrice) {
                return true
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }
    inner class WatchListViewHolder(private val binding: InsturmentRowItemBinding):
        RecyclerView.ViewHolder(binding.root){

        private fun animateCardBackground(view: MaterialCardView, fromColor: Int, toColor: Int) {
            view.animation?.cancel()

            val animator = ObjectAnimator.ofObject(
                view,
                "cardBackgroundColor",
                android.animation.ArgbEvaluator(),
                fromColor,
                toColor
            )
            animator.duration = 1000
            animator.start()
        }
        fun bind(item: WatchList, shouldAnimateBackground: Boolean){
            binding.apply {
                val formatPrice = "%.2f".format(item.currentPrice)
                val formatChangePercentage = "%.2f".format(item.priceChangePercent)
                val context = itemView.context

                val targetColor = if (formatChangePercentage.toFloat() > 0.0f) {
                    //yeşil renk
                    ContextCompat.getColor(context, com.talhakasikci.finn360fe.R.color.green_positive_color)
                } else if (formatChangePercentage.toFloat() < 0.0f) {
                    //kırmızı renk
                    ContextCompat.getColor(context, com.talhakasikci.finn360fe.R.color.red_negative_color)
                } else {
                    //nötr renk
                    ContextCompat.getColor(context, com.talhakasikci.finn360fe.R.color.colorSurfaceVariant)
                }

                instrumentNameTextView.text = item.description
                instrumentSymbolTextView.text = item.symbol
                instrumentCurrentPriceTV.text = "${formatPrice}$"
                instrumentChangePriceTV.text = "${formatChangePercentage}%"
                if (item.iconUrl != null) {
                    Glide.with(itemView).load(item.iconUrl).into(instrumentImageView)
                }


                if (shouldAnimateBackground) {
                    animateCardBackground(instrumentChangeCardView,targetColor , instrumentChangeCardView.cardBackgroundColor.defaultColor)

                }

                if (!item.iconUrl.isNullOrEmpty()){
                    Glide.with(itemView.context).load(item.iconUrl)
                        .into(instrumentImageView)
                } else { }

                itemView.setOnClickListener {
                    onStockClick(item)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchListViewHolder {
        val binding = InsturmentRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WatchListViewHolder(binding)
    }
    override fun onBindViewHolder(
        holder: WatchListViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), shouldAnimateBackground)
    }

    override fun onBindViewHolder(
        holder: WatchListViewHolder,
        position: Int,
        payloads: List<Any?>
    ) {
        if (payloads.isNotEmpty()){
            holder.bind(getItem(position), shouldAnimateBackground = true)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }


//    fun updateList(newItems: ArrayList<Instrument>) {
//        items = newItems
//        notifyDataSetChanged()
//    }
//

}