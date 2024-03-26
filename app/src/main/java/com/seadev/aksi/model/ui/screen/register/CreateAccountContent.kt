package com.seadev.aksi.model.ui.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seadev.aksi.model.domain.model.AksiUser
import com.seadev.aksi.model.ui.theme.AksiColor

@Composable
fun CreateAccountContent(
    onValidChange: (isValid: Boolean, user: AksiUser) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    val isValid by remember {
        derivedStateOf {
            phone.isNotBlank() && nik.isNotBlank() && nik.length == 16 && phone.length in 10..13
        }
    }

    LaunchedEffect(Unit) {
        Firebase.auth.currentUser?.let {
            it.displayName?.let { displayName -> name = displayName }
            it.email?.let { mail -> email = mail }
        }
    }

    LaunchedEffect(isValid) {
        onValidChange(
            isValid, AksiUser.Init.copy(
                email = email,
                fullName = name,
                phoneNumber = phone,
                nik = nik,
                uid = Firebase.auth.uid?:""
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Buat Akun",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = AksiColor.Text
            )
        }
        item {
            Text(
                text = "Langkah 1 dari 2",
                color = AksiColor.TextLight
            )
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                enabled = false,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("example@gmail.com") }
            )
        }
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                enabled = false,
                onValueChange = { name = it },
                label = { Text("Nama Lengkap") },
                placeholder = { Text("Si Bejo") }
            )
        }
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phone,
                onValueChange = { phone = it },
                maxLines = 1,
                prefix = { Text(text = "+62") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                label = { Text("Nomor Telepon") },
                placeholder = { Text("81xxxxxx") }
            )
        }
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nik,
                maxLines = 1,
                onValueChange = { if (it.length <= 16) nik = it },
                label = { Text("Nomor Kependudukan (NIK)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text("") }
            )
        }
    }
}