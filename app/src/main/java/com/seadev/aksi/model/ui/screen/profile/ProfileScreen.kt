package com.seadev.aksi.model.ui.screen.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seadev.aksi.model.BuildConfig
import com.seadev.aksi.model.domain.model.AksiUser
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.CircleSkeleton
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.screen.login.AuthViewModel
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import com.seadev.aksi.model.utils.DataUtils
import com.seadev.aksi.model.utils.ImageUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val user by authViewModel.currentUser.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        Firebase.auth.currentUser?.uid?.let { uid -> authViewModel.getUser(uid) {} }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navController::popBackStack) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        modifier = Modifier,
                        text = "Profil",
                        fontWeight = FontWeight.Bold,
                        color = if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {

                Box {
                    ItemProfileCard(
                        user,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 52.dp)
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(AksiColor.Accent)
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(4.dp),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        Firebase.auth.currentUser?.photoUrl
                                            ?: ImageUtils.IconProfile
                                    )
                                    .crossfade(true)
                                    .transformations(CircleCropTransformation())
                                    .build(),
                                contentDescription = "Profile",
                                loading = {
                                    CircleSkeleton(
                                        modifier = Modifier.shimmer(),
                                        size = 100.dp
                                    )
                                },
                                error = { Box(modifier = Modifier.size(100.dp)) }
                            )
                        }
                    }
                }
            }
            items(DataUtils.ProfileMenu.mainMenu) { menu ->
                ItemProfileMenu(title = menu.first, icon = menu.second) { iconMenu ->
                    when (iconMenu) {
                        ImageUtils.NavProfileIcon.IconNext -> navController.navigate(AksiNav.ASSESSMENT_HISTORY)
                        ImageUtils.NavProfileIcon.IconFeedBack -> {
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(BuildConfig.FEED_BACK_URL)
                                )
                            )
                        }

                        ImageUtils.NavProfileIcon.IconLogOut -> {
                            Firebase.auth.signOut()
                            navController.navigate(AksiNav.AUTH_ROUTE) {
                                popUpTo(navController.graph.id) { inclusive = true }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemProfileCard(
    aksiUser: AksiUser,
    modifier: Modifier = Modifier
) {
    AksiCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(38.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = aksiUser.fullName,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(24.dp),
                    model = ImageUtils.NavProfileIcon.IconCall,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(
                        if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                    ),
                    loading = {
                        RoundedSkeleton(
                            modifier = Modifier.shimmer(),
                            width = 24.dp, height = 24.dp
                        )
                    },
                    error = { Box(modifier = Modifier.size(24.dp)) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "+62${aksiUser.phoneNumber}")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(24.dp),
                    model = ImageUtils.NavProfileIcon.IconEmail,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(
                        if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                    ),
                    loading = {
                        RoundedSkeleton(
                            modifier = Modifier.shimmer(),
                            width = 24.dp, height = 24.dp
                        )
                    },
                    error = { Box(modifier = Modifier.size(24.dp)) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = aksiUser.email)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.size(24.dp),
                    model = ImageUtils.NavProfileIcon.IconLocation,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(
                        if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                    ),
                    loading = {
                        RoundedSkeleton(
                            modifier = Modifier.shimmer(),
                            width = 24.dp, height = 24.dp
                        )
                    },
                    error = { Box(modifier = Modifier.size(24.dp)) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = aksiUser.fullAddress)
            }
        }
    }
}

@Composable
private fun ItemProfileMenu(
    title: String,
    icon: String,
    onClick: (icon: String) -> Unit
) {
    AksiCard(
        onCardClick = { onClick.invoke(icon) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = title)
            Spacer(modifier = Modifier.weight(1f))
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(24.dp),
                model = icon,
                contentDescription = "",
                colorFilter = ColorFilter.tint(
                    if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                ),
                loading = {
                    RoundedSkeleton(
                        modifier = Modifier.shimmer(),
                        width = 24.dp, height = 24.dp
                    )
                },
                error = { Box(modifier = Modifier.size(24.dp)) }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    AksiKampungSiagaCovidTheme {
        ProfileScreen(rememberNavController())
    }
}