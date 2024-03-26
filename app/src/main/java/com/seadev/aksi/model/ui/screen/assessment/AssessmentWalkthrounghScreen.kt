package com.seadev.aksi.model.ui.screen.assessment

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import com.seadev.aksi.model.utils.DataUtils
import com.seadev.aksi.model.utils.ImageUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentWalkthroughScreen(
    navController: NavController
) {
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
                        text = "Kuesioner Penilaian Diri",
                        fontWeight = FontWeight.Bold,
                        color = if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                    )
                }
            )
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val (contentRef, btnStart) = createRefs()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(contentRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(btnStart.top, 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        model = ImageUtils.IlSelfAssessment,
                        contentDescription = "",
                        loading = {
                            RoundedSkeleton(
                                modifier = Modifier.shimmer(),
                                width = 250.dp, height = 250.dp, radius = 16.dp
                            )
                        },
                        error = { Box(modifier = Modifier.size(250.dp)) }
                    )
                }
                item {
                    Text(
                        text = "Petunjuk Pengisian",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AksiColor.Text
                    )
                }
                item {
                    Text(
                        text = "Pada kuesioner ini terdapat 3 langkah pertanyaan yang akan Anda isi, berikut penjelasannya.",
                        color = AksiColor.TextLight
                    )
                }
                item {
                    AssessmentStepItem()
                }
            }
            Button(
                onClick = { navController.navigate(AksiNav.ASSESSMENT_STEP) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AksiColor.Secondary
                ),
                modifier = Modifier.constrainAs(btnStart) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                Text(
                    text = "Mulai",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun AssessmentStepItem() {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (
            dotStep1, dotStep2, dotStep3, lineStep,
            titleStep1, descStep1, titleStep2, descStep2,
            titleStep3, descStep3
        ) = createRefs()
        Box(modifier = Modifier
            .width(10.dp)
            .clip(CircleShape)
            .background(AksiColor.Accent)
            .constrainAs(lineStep) {
                top.linkTo(dotStep1.top)
                start.linkTo(dotStep1.start)
                end.linkTo(dotStep1.end)
                bottom.linkTo(dotStep3.bottom)
                height = Dimension.fillToConstraints
            })

        Box(modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(AksiColor.Primary)
            .constrainAs(dotStep1) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(parent.start, 16.dp)
            })
        Text(
            text = DataUtils.SelfAssessmentQuestion.getStep(1).title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(titleStep1) {
                top.linkTo(dotStep1.top)
                start.linkTo(dotStep1.end, 16.dp)
                bottom.linkTo(dotStep1.bottom)
            },
            color = AksiColor.Text
        )
        Text(
            text = DataUtils.SelfAssessmentQuestion.getStep(1).desc,
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(descStep1) {
                top.linkTo(titleStep1.bottom, 4.dp)
                start.linkTo(dotStep1.end, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            color = AksiColor.Text
        )

        Box(modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(AksiColor.Primary)
            .constrainAs(dotStep2) {
                top.linkTo(descStep1.bottom, 16.dp)
                start.linkTo(parent.start, 16.dp)
            })
        Text(
            text = DataUtils.SelfAssessmentQuestion.getStep(2).title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(titleStep2) {
                top.linkTo(dotStep2.top)
                start.linkTo(dotStep2.end, 16.dp)
                bottom.linkTo(dotStep2.bottom)
            },
            color = AksiColor.Text
        )
        Text(
            text = DataUtils.SelfAssessmentQuestion.getStep(2).desc,
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(descStep2) {
                top.linkTo(titleStep2.bottom, 4.dp)
                start.linkTo(dotStep2.end, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            color = AksiColor.Text
        )

        Box(modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(AksiColor.Primary)
            .constrainAs(dotStep3) {
                top.linkTo(descStep2.bottom, 16.dp)
                start.linkTo(parent.start, 16.dp)
            })
        Text(
            text = DataUtils.SelfAssessmentQuestion.getStep(3).title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(titleStep3) {
                top.linkTo(dotStep3.top)
                start.linkTo(dotStep3.end, 16.dp)
                bottom.linkTo(dotStep3.bottom)
            },
            color = AksiColor.Text
        )
        Text(
            text = DataUtils.SelfAssessmentQuestion.getStep(3).desc,
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(descStep3) {
                top.linkTo(titleStep3.bottom, 4.dp)
                start.linkTo(dotStep3.end, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            color = AksiColor.Text
        )
    }
}

@Preview
@Composable
private fun PreviewAssessmentWalkthroughScreen() {
    AksiKampungSiagaCovidTheme {
        AssessmentWalkthroughScreen(rememberNavController())
    }
}