package com.seadev.aksi.model.ui.screen.assessment

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.colorAssessmentResult
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.imageAssessmentResult
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.resultListInstruction
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.resultStatusDesc
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.resultStepInstruction
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.resultTitle
import com.seadev.aksi.model.utils.ImageUtils

@Composable
fun AssessmentResultScreen(
    assessmentResult: DataUtils.SelfAssessmentQuestion.AssessmentResult,
    navController: NavController
) {
    val context = LocalContext.current
    BackHandler {
        navController.navigate(AksiNav.HOME) {
            popUpTo(AksiNav.HOME) { inclusive = true }
        }
    }
    Scaffold {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val (contentRef, btnFinish) = createRefs()

            Row(
                modifier = Modifier.constrainAs(btnFinish) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                },
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (assessmentResult in listOf(
                        DataUtils.SelfAssessmentQuestion.AssessmentResult.MID,
                        DataUtils.SelfAssessmentQuestion.AssessmentResult.HIGH,
                    )
                ) Button(
                    onClick = {
                        navController.navigate(AksiNav.HOTLINE) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AksiColor.Secondary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Daftar Hotline",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                if (assessmentResult == DataUtils.SelfAssessmentQuestion.AssessmentResult.LOW) Button(
                    onClick = {
                        navController.navigate(AksiNav.HOME) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AksiColor.Secondary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Selesai",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                if (assessmentResult in listOf(
                        DataUtils.SelfAssessmentQuestion.AssessmentResult.MID,
                        DataUtils.SelfAssessmentQuestion.AssessmentResult.HIGH,
                    )
                ) Button(
                    onClick = {
                        context.startActivity(
                            Intent(Intent.ACTION_DIAL, Uri.parse("tel:119"))
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AksiColor.Secondary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Box {
                        Text(
                            text = "Hubungi 119",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                                .padding(start = 12.dp),
                            textAlign = TextAlign.Center
                        )
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(14.dp),
                            model = ImageUtils.NavProfileIcon.IconCall,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(
                                if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                            ),
                            loading = {
                                RoundedSkeleton(
                                    modifier = Modifier.shimmer(),
                                    width = 14.dp, height = 14.dp
                                )
                            },
                            error = { Box(modifier = Modifier.size(14.dp)) }
                        )
                    }

                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(contentRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(btnFinish.top, 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val (backgroundRef, imageRef) = createRefs()
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(CircleShape)
                                .background(
                                    assessmentResult.colorAssessmentResult.copy(0.1f)
                                )
                                .constrainAs(backgroundRef) {
                                    top.linkTo(parent.top, (-80).dp)
                                    start.linkTo(parent.start, (-20).dp)
                                    end.linkTo(parent.end, (-20).dp)
                                    width = Dimension.fillToConstraints
                                }
                        )
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .size(imageSize)
                                .constrainAs(imageRef) {
                                    top.linkTo(backgroundRef.bottom, (-70).dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            model = assessmentResult.imageAssessmentResult,
                            contentDescription = "",
                            loading = {
                                RoundedSkeleton(
                                    modifier = Modifier.shimmer(),
                                    width = imageSize, height = imageSize, radius = 16.dp
                                )
                            },
                            error = { Box(modifier = Modifier.size(imageSize)) }
                        )
                    }
                }
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = assessmentResult.resultTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = assessmentResult.colorAssessmentResult
                    )
                }
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center,
                        text = assessmentResult.resultStatusDesc,
                        color = AksiColor.TextLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(top = 16.dp),
                        text = assessmentResult.resultStepInstruction,
                        color = AksiColor.TextLight,
                        fontSize = 14.sp
                    )
                }
                item {
                    Column {
                        assessmentResult.resultListInstruction.forEach {
                            ConstraintLayout(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 42.dp, end = 16.dp),
                            ) {
                                val (dotRef, textRef) = createRefs()
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(AksiColor.Text)
                                        .constrainAs(dotRef) {
                                            top.linkTo(parent.top, 7.dp)
                                            start.linkTo(parent.start)
                                        }
                                )
                                Text(
                                    modifier = Modifier
                                        .constrainAs(textRef) {
                                            top.linkTo(parent.top)
                                            start.linkTo(dotRef.end, 8.dp)
                                            end.linkTo(parent.end)
                                            width = Dimension.fillToConstraints
                                        },
                                    text = it,
                                    color = AksiColor.TextLight,
                                    lineHeight = 18.sp,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

private val imageSize = 150.dp

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PreviewAssessmentResultScreen() {
    AksiKampungSiagaCovidTheme {
        AssessmentResultScreen(
            assessmentResult = DataUtils.SelfAssessmentQuestion.AssessmentResult.HIGH,
            rememberNavController()
        )
    }
}