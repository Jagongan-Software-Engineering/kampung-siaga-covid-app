package com.seadev.aksi.model.ui.screen.login

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seadev.aksi.model.R
import com.seadev.aksi.model.data.remote.api.GoogleAuthApi
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import com.seadev.aksi.model.utils.ImageUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            if (result.data?.action == ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val exception: Exception? = result.data?.getSerializableExtra(
                        ActivityResultContracts.StartIntentSenderForResult.EXTRA_SEND_INTENT_EXCEPTION,
                        Exception::class.java
                    )
                    Log.e("LOG", "Couldn't start One Tap UI: ${exception?.localizedMessage}")
                } else Log.e("LOG", "Couldn't start One Tap UI: ${result.data?.action}")
            }
            return@rememberLauncherForActivityResult
        }
        val oneTapClient = Identity.getSignInClient(context)
        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
        credential.googleIdToken?.let { idToken ->
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.signInWithCredential(firebaseCredential)
                .addOnSuccessListener {
                    Firebase.auth.currentUser?.let {
                        if (it.uid.isNotBlank()) {
                            authViewModel.getUser(it.uid) { isUserFound ->
                                if (isUserFound) navController.navigate(AksiNav.HOME) {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                } else navController.navigate(AksiNav.REGISTER)
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Log.w("TAG", "signInWithCredential:failure", it)
                }
        }
    }

    Scaffold {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val (iconRef, titleRef, descRef, googleRef, illustrationRef) = createRefs()
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(150.dp)
                    .constrainAs(iconRef) {
                        top.linkTo(parent.top, 68.dp)
                        start.linkTo(parent.start, 32.dp)
                    },
                model = ImageUtils.LogoAksi, contentDescription = "",
                loading = {
                    RoundedSkeleton(
                        modifier = Modifier.shimmer(),
                        width = 150.dp, height = 150.dp, radius = 8.dp
                    )
                }
            )
            Text(
                text = "Selamat Datang",
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(iconRef.bottom, 32.dp)
                    start.linkTo(parent.start, 32.dp)
                    end.linkTo(parent.end, 32.dp)
                    width = Dimension.fillToConstraints
                },
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = AksiColor.Text
            )
            Text(
                text = "Masuk untuk melanjutkan",
                modifier = Modifier.constrainAs(descRef) {
                    top.linkTo(titleRef.bottom, 4.dp)
                    start.linkTo(parent.start, 32.dp)
                    end.linkTo(parent.end, 32.dp)
                    width = Dimension.fillToConstraints
                },
                color = AksiColor.TextLight
            )
            Image(
                modifier = Modifier.constrainAs(illustrationRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.il_aksi_login), contentDescription = ""
            )
            Card(
                modifier = Modifier.constrainAs(googleRef) {
                    bottom.linkTo(parent.bottom, 32.dp)
                    start.linkTo(parent.start, 32.dp)
                    end.linkTo(parent.end, 32.dp)
                    width = Dimension.fillToConstraints
                },
                colors = CardDefaults.cardColors(
                    containerColor = AksiColor.Background
                ),
                onClick = {
                    val oneTapClient = Identity.getSignInClient(context)
                    val signInRequest = GoogleAuthApi.signRequest
                    try {
                        coroutineScope.launch {
                            val result = oneTapClient.beginSignIn(signInRequest).await()
                            val intentSenderRequest =
                                IntentSenderRequest.Builder(result.pendingIntent).build()
                            launcher.launch(intentSenderRequest)
                        }
                    } catch (e: Exception) {
                        Log.d("LOG", e.message.toString())
                    }
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.size(24.dp),
                        model = ImageUtils.IconGoogle, contentDescription = "",
                        loading = {
                            RoundedSkeleton(
                                modifier = Modifier.shimmer(),
                                width = 24.dp, height = 24.dp, radius = 6.dp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Masuk dengan Google",
                        color = AksiColor.Text
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    AksiKampungSiagaCovidTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}