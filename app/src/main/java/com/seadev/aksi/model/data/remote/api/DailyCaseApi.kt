package com.seadev.aksi.model.data.remote.api

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class DailyCaseApi {
    private val path = Firebase.firestore.collection("daily_case")
    val summary = path.document("summary")
    val distribution = path.document("distribution").collection("province")
}