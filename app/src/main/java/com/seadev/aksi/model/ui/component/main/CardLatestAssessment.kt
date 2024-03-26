package com.seadev.aksi.model.ui.component.main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
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
import com.seadev.aksi.model.domain.model.HistoryAssessment
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import com.seadev.aksi.model.utils.DataUtils
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.colorAssessmentResult
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.imageAssessmentResult
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.resultStatusDesc
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.resultTitle
import com.seadev.aksi.model.utils.DateUtils.formatTimeFull

@Composable
fun CardLatestAssessment(
    navController: NavController,
    historyAssessment: HistoryAssessment,
) {
    val assessmentResult by remember {
        mutableStateOf(DataUtils.SelfAssessmentQuestion.AssessmentResult.entries.find { r ->
            r.name == historyAssessment.assessmentResult
        } ?: DataUtils.SelfAssessmentQuestion.AssessmentResult.NONE)
    }
    AksiCard(
        onCardClick = {
            navController.navigate(AksiNav.ASSESSMENT_RESULT + "/${assessmentResult.name}")
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            val (backgroundRef, imageRef, titleRef, descRef, dateRef) = createRefs()
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(assessmentResult.colorAssessmentResult.copy(0.1f))
                    .constrainAs(backgroundRef) {
                        top.linkTo(parent.top, (-50).dp)
                        start.linkTo(parent.start, (-70).dp)
                    }
            )
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(imageSize)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        bottom.linkTo(parent.bottom)
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
            Text(
                modifier = Modifier
                    .constrainAs(titleRef) {
                        top.linkTo(imageRef.top)
                        start.linkTo(imageRef.end, 8.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    },
                text = assessmentResult.resultTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = assessmentResult.colorAssessmentResult
            )
            Text(
                modifier = Modifier
                    .constrainAs(descRef) {
                        top.linkTo(titleRef.bottom, 4.dp)
                        start.linkTo(imageRef.end, 8.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    },
                text = assessmentResult.resultStatusDesc,
                color = AksiColor.Text,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
            Text(
                modifier = Modifier
                    .constrainAs(dateRef) {
                        top.linkTo(descRef.bottom, 4.dp)
                        start.linkTo(imageRef.end, 8.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    },
                text = historyAssessment.dateCreated.formatTimeFull,
                color = AksiColor.TextLight,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun TitleAssessment(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Riwayat Penilaian Diri",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = AksiColor.Text
        )
        Spacer(modifier = Modifier.weight(1f))
        AssistChip(
            onClick = { navController.navigate(AksiNav.ASSESSMENT_HISTORY) },
            border = AssistChipDefaults.assistChipBorder(
                borderWidth = 0.dp,
                borderColor = Color.Transparent
            ),
            label = {
                Text(
                    text = "Semua",
                    color = if (isSystemInDarkTheme()) AksiColor.TextLight else AksiColor.Primary
                )
            }
        )
    }
}

private val imageSize = 80.dp

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PreviewCardLatestAssessment() {
    AksiKampungSiagaCovidTheme {
        CardLatestAssessment(
            rememberNavController(),
            HistoryAssessment.Init
        )
    }
}