package com.example.mygifts.model

import android.arch.lifecycle.LiveData
import android.media.Image


data class Contact(
    private
    var name: String,
    var surname: String,
    var image: Image,
    var description: String,
    val gifts: LiveData<List<Gift>>
)
