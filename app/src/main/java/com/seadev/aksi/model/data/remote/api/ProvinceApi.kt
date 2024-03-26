package com.seadev.aksi.model.data.remote.api

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProvinceApi {
    val province = Firebase.firestore.collection("province")
}