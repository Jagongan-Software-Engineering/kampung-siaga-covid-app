package com.seadev.aksi.model.ui.component.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seadev.aksi.model.ui.component.CircleSkeleton
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.ImageUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    onProfileClick: () -> Unit
) {
    TopAppBar(title = { }, actions = {
        ConstraintLayout(
            modifier = Modifier
                .height(64.dp)
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            val (iconMain, iconProfile) = createRefs()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(iconMain) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.size(38.dp),
                    model = ImageUtils.LogoITTP,
                    loading = {
                        RoundedSkeleton(
                            modifier = Modifier.shimmer(),
                            width = 38.dp, height = 38.dp, radius = 4.dp
                        )
                    },
                    error = { Box(modifier = Modifier.size(38.dp)) },
                    contentDescription = "Logo Telkom Institute of Technology Purwokerto"
                )
                SubcomposeAsyncImage(
                    modifier = Modifier.height(38.dp),
                    model = ImageUtils.LogoAksi,
                    loading = {
                        RoundedSkeleton(
                            modifier = Modifier.shimmer(),
                            width = 38.dp, height = 38.dp, radius = 4.dp
                        )
                    },
                    error = { Box(modifier = Modifier.size(38.dp)) },
                    contentDescription = "Logo Aksi"
                )
            }
            IconButton(
                modifier = Modifier.constrainAs(iconProfile) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = AksiColor.Accent
                ),
                onClick = onProfileClick
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.size(32.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Firebase.auth.currentUser?.photoUrl?:ImageUtils.IconProfile)
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = "Profile",
                    loading = {
                        CircleSkeleton(
                            modifier = Modifier.shimmer(),
                            size = 32.dp
                        )
                    },
                    error = { Box(modifier = Modifier.size(32.dp))}
                )
            }
        }
    })
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF3C3C3C,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun PreviewMainTopBar() {
    AksiKampungSiagaCovidTheme {
        MainTopBar {}
    }
}