package com.aarya.mybinggchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.aarya.mybinggchat.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var messageBox: String
    private lateinit var adapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var receivingUser: User
    private lateinit var auth : FirebaseAuth
    private lateinit var dbRef : DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_chat)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        dbRef = Firebase.database.reference
        val senderUid = auth.currentUser?.uid!!
        receivingUser = intent.getParcelableExtra("userDetail")!!
        senderRoom = receivingUser.uid + senderUid
        receiverRoom = senderUid + receivingUser.uid

        supportActionBar?.title = receivingUser.name
        messageList = ArrayList()
        adapter = MessageAdapter(messageList)
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.adapter = adapter

        // Logic for adding data to recycler view
        dbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener( object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()
                    for( postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue<Message>()
                        messageList.add(message!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        // Logic for adding message to Database
        binding.btnSend.setOnClickListener {
            messageBox = binding.etMessageBox.text.toString()
            val messageObject = Message( messageBox, senderUid)
            dbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.etMessageBox.setText("")
        }
    }
}