package com.xyz.mvvm_room.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xyz.mvvm_room.database.User
import com.xyz.mvvm_room.interfaces.RecyclerClickListener
import kotlinx.android.synthetic.main.item_layout.view.*

class UserListViewHolder(
    private val view: View,
    private val listener: RecyclerClickListener
) : RecyclerView.ViewHolder(view) {

    fun setData(user: User) {
        view.apply {
            tvName.text = user.firstName
            tvLastName.text = user.lastName

            ivEdit.setOnClickListener {
                listener.onEditClicked(adapterPosition, user)
            }

            ivDelete.setOnClickListener {
                listener.onDeleteClicked(adapterPosition, user)
            }
        }
    }
}