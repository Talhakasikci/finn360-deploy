package com.talhakasikci.finn360fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.talhakasikci.finn360fe.adapter.SearchSymbolAdapter.ViewHolder
import com.talhakasikci.finn360fe.databinding.SearchSymbolRowItemBinding
import com.talhakasikci.finn360fe.models.Search


class SearchSymbolAdapter(private var items: ArrayList<Search>,
                          private val onStockClick: (Search) -> Unit): RecyclerView.Adapter<ViewHolder>() {
    inner class ViewHolder(private val binding: SearchSymbolRowItemBinding): RecyclerView.ViewHolder(binding.root)  {
        var isFavorite: Boolean = false
        fun bind(item: Search){
            binding.apply {
                instrumentSymbolTextView.text = item.symbol
                instrumentNameTextView.text = item.description
                item.iconUrl?.let {

                }
                if (item.iconUrl != null ){
                    Glide.with(itemView).load(item.iconUrl)
                        .into(instrumentLogoImageView)
                }else {
                    instrumentLogoImageView.setImageResource(com.talhakasikci.finn360fe.R.drawable.icon_no_images)
                }



                updateFavoriteButtonState(isFavorite!!, favoriteButton )
                favoriteButton.setOnClickListener {
                    if (isFavorite!!){
                        //favorilerden çıkar
                        isFavorite = false
                        updateFavoriteButtonState(isFavorite!!, favoriteButton )
                    }
                    else {
                        //favorilere ekle
                        isFavorite = true
                        updateFavoriteButtonState(isFavorite!!, favoriteButton )
                    }
                    // Here you would typically also update the data source to reflect the new favorite state
                }
                itemView.setOnClickListener {
                    onStockClick(item)
                }

            }
        }

        private fun updateFavoriteButtonState(isFavorite: Boolean, button: MaterialButton) {
            // This function can be used to update the favorite button state if needed
            // Implementation can be added here

            if(isFavorite){
                button.icon = button.context.resources.getDrawable(com.talhakasikci.finn360fe.R.drawable.star_filled_icon, null)
            } else {
                button.icon = button.context.resources.getDrawable(com.talhakasikci.finn360fe.R.drawable.star_icon, null)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = SearchSymbolRowItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {


        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(newItems: ArrayList<Search>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }




}