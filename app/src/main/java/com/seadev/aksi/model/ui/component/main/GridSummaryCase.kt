package com.seadev.aksi.model.ui.component.main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
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
import com.seadev.aksi.model.domain.model.SummaryCase
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.CircleSkeleton
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.formatCaseNumber

@Composable
fun GridSummaryCase(
    summaryCase: SummaryCase
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row {
            CardSummaryCase(
                modifier = Modifier.weight(1f),
                summaryLabel = "Total Kasus",
                summaryCase = summaryCase.positive
            )
            Spacer(modifier = Modifier.width(16.dp))
            CardSummaryCase(
                modifier = Modifier.weight(1f),
                colorIndicator = AksiColor.Green,
                summaryLabel = "Sembuh",
                summaryCase = summaryCase.recover
            )
        }
        Row {
            CardSummaryCase(
                modifier = Modifier.weight(1f),
                colorIndicator = AksiColor.Yellow,
                summaryLabel = "Dirawat",
                summaryCase = summaryCase.inCare
            )
            Spacer(modifier = Modifier.width(16.dp))
            CardSummaryCase(
                modifier = Modifier.weight(1f),
                colorIndicator = AksiColor.Red,
                summaryLabel = "Meninggal",
                summaryCase = summaryCase.died
            )
        }
    }
}

@Composable
fun CardSummaryCase(
    modifier: Modifier = Modifier,
    colorIndicator: Color = Color.Gray,
    summaryLabel: String, summaryCase: Int
) {
    AksiCard(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (indicator, label, textCase) = createRefs()
            Box(modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(colorIndicator)
                .constrainAs(indicator) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
            Text(
                modifier = Modifier.constrainAs(label) {
                    top.linkTo(indicator.top)
                    start.linkTo(indicator.end, 8.dp)
                    bottom.linkTo(indicator.bottom)
                },
                text = summaryLabel,
                color = AksiColor.TextLight
            )
            Text(
                modifier = Modifier.constrainAs(textCase) {
                    top.linkTo(indicator.bottom, 16.dp)
                    start.linkTo(label.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                text = summaryCase.formatCaseNumber(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AksiColor.Text
            )
        }
    }
}

@Composable
private fun SkeletonCardSummaryCase(
    modifier: Modifier = Modifier
) {
    AksiCard(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (indicator, label, textCase) = createRefs()
            CircleSkeleton(modifier = Modifier
                .constrainAs(indicator) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                size = 10.dp
            )
            RoundedSkeleton(
                modifier = Modifier.constrainAs(label) {
                    top.linkTo(indicator.top)
                    start.linkTo(indicator.end, 8.dp)
                    bottom.linkTo(indicator.bottom)
                },
                width = 50.dp,
                height = 14.dp
            )
            RoundedSkeleton(
                modifier = Modifier.constrainAs(textCase) {
                    top.linkTo(indicator.bottom, 16.dp)
                    start.linkTo(label.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                width = 100.dp,
                height = 20.dp
            )
        }
    }
}

@Composable
fun SkeletonGridSummaryCase(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shimmer(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row {
            SkeletonCardSummaryCase(
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(16.dp))
            SkeletonCardSummaryCase(
                modifier = Modifier.weight(1f)
            )
        }
        Row {
            SkeletonCardSummaryCase(
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            SkeletonCardSummaryCase(
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(
    showBackground = true, backgroundColor = 0xFFFAFAFA,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    showBackground = true, backgroundColor = 0xFF3C3C3C,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun PreviewGridSummaryCase() {
    AksiKampungSiagaCovidTheme {
        GridSummaryCase(
            SummaryCase.Init.copy(
                positive = 6417490,
                recover = 6236021,
                inCare = 23503,
                died = 157966
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSkeletonCardSummaryCase(){
    AksiKampungSiagaCovidTheme {
        SkeletonGridSummaryCase()
    }
}