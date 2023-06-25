package com.aarya.mybinggchat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aarya.mybinggchat.databinding.UserRowItemBinding

class UserAdapter( private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    inner class MyViewHolder( var binding: UserRowItemBinding): RecyclerView.ViewHolder(binding.root)

    var onItemClick : ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding){
            with(userList[position]){
                tvUser.text = this.name
            }
        }

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(userList[position])
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }



}