package com.example.mygifts.model

import android.media.Image
import java.util.*

data class Gift(
    var name: String,
    var url: String,
    var image: Image,
    var description: String,
    var date: Date
)