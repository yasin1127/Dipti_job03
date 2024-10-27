package com.example.locationsharingapp_dipti_ict_amad_l4_04_11.Adapter_dipti_ict_amad_l4_04_11

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.databinding.ItemUserDiptiIctAmadL40411Binding
import com.example.locationsharingapp_dipti_ict_amad_l4_04_11.model_dipti_ict_amad_l4_04_11.User_dipti_ict_amad_l4_04_11

class UserAdapter_dipti_ict_amad_l4_04_11(private var userList: List<User_dipti_ict_amad_l4_04_11>) : RecyclerView.Adapter<UserAdapter_dipti_ict_amad_l4_04_11.UserViewHolder>() {

    class UserViewHolder(private val binding: ItemUserDiptiIctAmadL40411Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User_dipti_ict_amad_l4_04_11) {
            binding.apply {
                displayNameTxt15.text = user.displayName
                emailTxt15.text = user.email
                locationTxt15.text = user.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserDiptiIctAmadL40411Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    fun updateData(newList: List<User_dipti_ict_amad_l4_04_11>) {
        userList = newList
        notifyDataSetChanged()
    }
}
