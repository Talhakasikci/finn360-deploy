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

import com.talhakasikci.finn360fe.models.Instrument

class StockMarketCVAdapter(private val onStockClick: (Instrument) -> Unit): ListAdapter<Instrument, StockMarketViewHolder>(InstrumentDiffCallback()) {
    var shouldAnimateBackground = false
    class InstrumentDiffCallback : DiffUtil.ItemCallback<Instrument> () {
        override fun areItemsTheSame(oldItem: Instrument, newItem: Instrument): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: Instrument, newItem: Instrument): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Instrument, newItem: Instrument): Any? {
            if (oldItem.changePercentage != newItem.changePercentage || oldItem.price != newItem.price) {
                return true
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }
    inner class StockMarketViewHolder(private val binding: InsturmentRowItemBinding):
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
        fun bind(item: Instrument, shouldAnimateBackground: Boolean){
            binding.apply {
                val formatPrice = "%.2f".format(item.price)
                val formatChangePercentage = "%.2f".format(item.changePercentage)
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

                instrumentNameTextView.text = item.name
                instrumentSymbolTextView.text = item.symbol
                instrumentCurrentPriceTV.text = "${formatPrice}$"
                instrumentChangePriceTV.text = "${formatChangePercentage}%"


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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockMarketViewHolder {
        val binding = InsturmentRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StockMarketViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: StockMarketViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), shouldAnimateBackground)
    }

    override fun onBindViewHolder(
        holder: StockMarketViewHolder,
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