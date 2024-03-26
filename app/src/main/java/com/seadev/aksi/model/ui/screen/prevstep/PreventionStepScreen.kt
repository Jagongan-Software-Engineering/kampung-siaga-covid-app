package com.seadev.aksi.model.ui.screen.prevstep

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.DataUtils

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PreventionStepScreen(
    preventionIndex: Int,
    navController: NavController
) {
    val prevention by remember {
        mutableStateOf(DataUtils.Prevention.mainPrevention[preventionIndex])
    }
    val preventionStep by remember {
        mutableStateOf(
            when (preventionIndex) {
                0 -> DataUtils.PreventionStep.about
                1 -> DataUtils.PreventionStep.symptom
                2 -> DataUtils.PreventionStep.prevent
                3 -> DataUtils.PreventionStep.notAllowed
                else -> DataUtils.PreventionStep.about
            }
        )
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
                        text = prevention.title,
                        fontWeight = FontWeight.Bold,
                        color = if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                    )
                }
            )
        }
    ) {
        val pagerState = rememberPagerState(pageCount = {
            preventionStep.size
        })
        val fling = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(1)
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val (pager, content, indicator) = createRefs()
            HorizontalPager(
                modifier = Modifier.constrainAs(pager) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("1:1")
                },
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                beyondBoundsPageCount = 1,
                flingBehavior = fling,
                contentPadding = PaddingValues(16.dp),
                pageSpacing = 16.dp
            ) { page ->
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = preventionStep[page].image,
                    contentDescription = "",
                    loading = {
                        RoundedSkeleton(width = sizeImage, height = sizeImage, radius = 16.dp)
                    },
                    error = {
                        Box(modifier = Modifier.size(sizeImage))
                    }
                )
            }
            AksiCard(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(pager.bottom, -72.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = preventionStep[pagerState.currentPage].title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AksiColor.Text
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = preventionStep[pagerState.currentPage].desc,
                        fontSize = 14.sp,
                        color = AksiColor.TextLight
                    )
                }
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .constrainAs(indicator) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) AksiColor.Primary else Color.LightGray
                    if (pagerState.currentPage == iteration) Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .width(56.dp)
                            .height(12.dp)
                    ) else Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)
                    )
                }
            }
        }
    }
}

private val sizeImage = 250.dp

@Preview
@Composable
private fun PreviewPreventionStepScreen() {
    AksiKampungSiagaCovidTheme {
        PreventionStepScreen(2, rememberNavController())
    }
}