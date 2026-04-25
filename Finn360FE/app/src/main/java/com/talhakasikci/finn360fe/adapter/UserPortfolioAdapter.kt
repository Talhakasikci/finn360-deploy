package com.talhakasikci.finn360fe.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.talhakasikci.finn360fe.adapter.UserPortfolioAdapter.PortfolioViewHolder
import com.talhakasikci.finn360fe.databinding.PortfolioRowItemBinding
import com.talhakasikci.finn360fe.models.Portfolio

class UserPortfolioAdapter(private val portfolio: ArrayList<Portfolio>):
    ListAdapter<Portfolio, PortfolioViewHolder>(PortfolioDiffCallback()) {

    inner class PortfolioViewHolder(private val binding: PortfolioRowItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: Portfolio){
            binding.apply {
                instrumentSymbolTextView.text = item.symbol
                instrumentNameTextView.text = item.description
                instrumentPriceTextView.text = "${item.currentPrice}"
                instrumenDailyPriceChangeTV.text = "${item.changePercentage}%"
                instrumentTotalPriceTV.text = "${item.quantity * item.currentPrice}$"
                item.iconUrl?.let {
                    Glide.with(itemView).load(item.iconUrl).into(instrumentLogoImageView)
                }
                instrumenLotsPiecesTV.text = "${item.quantity} pcs"

            }

        }
    }

    open class PortfolioDiffCallback: DiffUtil.ItemCallback<Portfolio>() {
        override fun areItemsTheSame(
            oldItem: Portfolio,
            newItem: Portfolio
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: Portfolio,
            newItem: Portfolio
        ): Boolean {
        return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Portfolio, newItem: Portfolio): Any? {
            if (oldItem.currentPrice != newItem.currentPrice || oldItem.changePercentage != newItem.changePercentage || oldItem.quantity != newItem.quantity) {
                return true
            }
            return super.getChangePayload(oldItem, newItem)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PortfolioViewHolder {
        val binding = PortfolioRowItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return PortfolioViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PortfolioViewHolder,
        position: Int
    ) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

//    override fun getItemCount(): Int {
//        return portfolio.size
//    }

//    fun updateList(newItems: ArrayList<Portfolio>) {
//        portfolio.clear()
//        portfolio.addAll(newItems)
//        notifyDataSetChanged()
//    }




}
