package com.seadev.aksi.model.ui.screen.prevention

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import com.seadev.aksi.model.utils.DataUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreventionScreen(
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navHostController::popBackStack) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        modifier = Modifier,
                        text = "Pencegahan",
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    SubcomposeAsyncImage(
                        modifier = Modifier.size(150.dp),
                        model = DataUtils.Prevention.Header.image,
                        contentDescription = "",
                        loading = {
                            RoundedSkeleton(
                                modifier = Modifier.shimmer(),
                                width = 150.dp, height = 150.dp, radius = 6.dp
                            )
                        },
                        error = {
                            Box(modifier = Modifier.size(150.dp))
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = DataUtils.Prevention.Header.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AksiColor.Text
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = DataUtils.Prevention.Header.desc,
                        color = AksiColor.TextLight
                    )
                }
            }
            items(DataUtils.Prevention.mainPrevention) { prevention ->
                AksiCard(
                    onCardClick = {
                        navHostController.navigate(
                            AksiNav.PREVENTION_STEP +
                                    "/${DataUtils.Prevention.mainPrevention.indexOf(prevention)}"
                        )
                    }
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        val (imageRef, titleRef) = createRefs()
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .size(100.dp)
                                .constrainAs(imageRef) {
                                    bottom.linkTo(parent.bottom, (-18).dp)
                                    start.linkTo(parent.start, (-16).dp)
                                },
                            model = prevention.image,
                            contentDescription = "",
                            loading = {
                                RoundedSkeleton(
                                    modifier = Modifier.shimmer(),
                                    width = 100.dp, height = 100.dp, radius = 6.dp
                                )
                            },
                            error = {
                                Box(modifier = Modifier.size(100.dp))
                            }
                        )
                        Text(
                            modifier = Modifier.constrainAs(titleRef) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(imageRef.end, 16.dp)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                            text = prevention.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPreventionScreen() {
    AksiKampungSiagaCovidTheme {
        PreventionScreen(rememberNavController())
    }
}