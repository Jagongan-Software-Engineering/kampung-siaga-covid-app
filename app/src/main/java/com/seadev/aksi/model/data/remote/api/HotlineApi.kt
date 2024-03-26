package com.seadev.aksi.model.data.remote.api

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class HotlineApi {
    val hotline = Firebase.firestore.collection("hotline")
}