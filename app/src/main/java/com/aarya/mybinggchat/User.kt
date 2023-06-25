package com.aarya.mybinggchat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String ="",
    val email:String ="",
    val uid: String =""
):Parcelable
