package com.talhakasikci.finn360fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.databinding.ForumRowItemBinding
import com.talhakasikci.finn360fe.models.Article

class ArticlesAdapter(private val items: ArrayList<Article>):
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ForumRowItemBinding): RecyclerView.ViewHolder(binding.root) {
        var isFollowing: Boolean? = null

        fun bind(item: Article){
            binding.apply {
                articleUserNameTextView.text = item.userName
                articleUserSurnameTextView.text = item.userSurname
                articleTitleTextView.text = item.title
                articleBodyTextView.text = item.body
                articleUserEmailTextView.text = item.userEmail

                isFollowing = item.isFollowingUser

                updateFollowButtonState(isFollowing!!, followButton)

                followButton.setOnClickListener {
                    if (isFollowing!!){
                        //takibi bırak
                        isFollowing = false
                        updateFollowButtonState(isFollowing!!, followButton)
                    }
                    else {
                        //takip et
                        isFollowing = true
                        updateFollowButtonState(isFollowing!!, followButton)
                    }
                    // Here you would typically also update the data source to reflect the new follow state
                }
            }
        }
        private fun updateFollowButtonState(isFollowingUser: Boolean, button: MaterialButton) {
            // This function can be used to update the follow button state if needed
            val context = button.context

            if(isFollowingUser == true) {
                button.icon = context.resources.getDrawable(R.drawable.follow_person_icon, null)
                button.text = "Following"
                Toast.makeText(context, "followed!", Toast.LENGTH_SHORT).show()
            } else {
                button.icon = context.resources.getDrawable(R.drawable.unfollow_person_icon, null)
                button.text = "Follow"
                Toast.makeText(context, "Unfollowed!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ForumRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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







}