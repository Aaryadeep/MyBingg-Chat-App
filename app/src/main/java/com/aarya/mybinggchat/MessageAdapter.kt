package com.aarya.mybinggchat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aarya.mybinggchat.databinding.ReceivedRowItemBinding
import com.aarya.mybinggchat.databinding.SentRowItemBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val messageList: ArrayList<Message>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class SentViewHolder( var binding: SentRowItemBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceiveViewHolder( var binding: ReceivedRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val sentBinding = SentRowItemBinding.inflate( LayoutInflater.from(parent.context), parent , false)
        val receiveBinding = ReceivedRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        if( viewType==1){
            return SentViewHolder(sentBinding)
        }
        else{
            return ReceiveViewHolder(receiveBinding)
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if( FirebaseAuth.getInstance().currentUser?.uid == currentMessage.senderUid ){
            1;
        } else 2;
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if( holder.javaClass == SentViewHolder::class.java){
            val sentViewHolder = holder as SentViewHolder
            with(sentViewHolder.binding){
                with(messageList[position]){
                    tvSentMesg.text = this.message
                }
            }
        }
        else{
            val recViewHolder = holder as ReceiveViewHolder
            with(recViewHolder.binding){
                with(messageList[position]){
                    tvReceiveMesg.text = this.message
                }
            }
        }
    }

}