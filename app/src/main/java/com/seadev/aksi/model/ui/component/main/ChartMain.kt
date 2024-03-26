package com.seadev.aksi.model.ui.component.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.seadev.aksi.model.domain.model.SummaryCase
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.CircleSkeleton
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.formatCaseNumber
import com.seadev.aksi.model.utils.formatPercentCase

@Composable
fun ChartMain(
    summaryCase: SummaryCase
) {
    AksiCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (chart, infoLegend, infoData, textData) = createRefs()
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .constrainAs(chart) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
            ) {
                PieChart(
                    data = mapOf(
                        Pair("Dirawat", summaryCase.inCare),
                        Pair("Sembuh", summaryCase.recover),
                        Pair("Meninggal", summaryCase.died)
                    )
                )
                if (summaryCase.positive != 0) Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = summaryCase.positive.formatCaseNumber(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = AksiColor.Text
                    )
                    Text(
                        text = "Kasus",
                        color = AksiColor.TextLight
                    )
                } else Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Data tidak tersedia",
                    color = AksiColor.Yellow
                )
            }
            Column(
                modifier = Modifier.constrainAs(infoData) {
                    top.linkTo(chart.top)
                    bottom.linkTo(chart.bottom)
                    end.linkTo(parent.end, 8.dp)
                }
            ) {
                Text(
                    text = "Dirawat",
                    fontSize = 14.sp,
                    color = AksiColor.TextLight
                )
                Text(
                    text = "${(summaryCase.inCare.toFloat() / summaryCase.positive.toFloat()).formatPercentCase()}%",
                    fontSize = 20.sp,
                    color = AksiColor.Text,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sembuh",
                    fontSize = 14.sp,
                    color = AksiColor.TextLight
                )
                Text(
                    text = "${(summaryCase.recover.toFloat() / summaryCase.positive.toFloat()).formatPercentCase()}%",
                    fontSize = 20.sp,
                    color = AksiColor.Text,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Meninggal",
                    fontSize = 14.sp,
                    color = AksiColor.TextLight
                )
                Text(
                    text = "${(summaryCase.died.toFloat() / summaryCase.positive.toFloat()).formatPercentCase()}%",
                    fontSize = 20.sp,
                    color = AksiColor.Text,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier.constrainAs(infoLegend) {
                    top.linkTo(chart.bottom, 22.dp)
                    start.linkTo(parent.start, 8.dp)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(AksiColor.Green)
                )
                Text(
                    text = "Sembuh",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(AksiColor.Yellow)
                )
                Text(
                    text = "Dirawat",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(AksiColor.Red)
                )
                Text(
                    text = "Meninggal",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Text(
                modifier = Modifier.constrainAs(textData) {
                    top.linkTo(infoLegend.bottom)
                    start.linkTo(parent.start, 8.dp)
                },
                text = "Data Covid-19 Indonesia",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SkeletonChartMain() {
    AksiCard(
        modifier = Modifier
            .fillMaxWidth()
            .shimmer()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row {
                RoundedSkeleton(width = 220.dp, height = 150.dp, radius = 8.dp)
                Spacer(modifier = Modifier.weight(0.2f))
                Column(
                    modifier = Modifier.weight(0.3f)
                ) {
                    RoundedSkeleton(width = 40.dp, height = 12.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    RoundedSkeleton(width = 50.dp, height = 22.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    RoundedSkeleton(width = 48.dp, height = 12.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    RoundedSkeleton(width = 70.dp, height = 22.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    RoundedSkeleton(width = 52.dp, height = 12.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    RoundedSkeleton(width = 38.dp, height = 22.dp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                CircleSkeleton(size = 10.dp)
                Spacer(modifier = Modifier.width(2.dp))
                RoundedSkeleton(width = 40.dp, height = 10.dp, radius = 10.dp)
                Spacer(modifier = Modifier.width(8.dp))
                CircleSkeleton(size = 10.dp)
                Spacer(modifier = Modifier.width(2.dp))
                RoundedSkeleton(width = 35.dp, height = 10.dp, radius = 10.dp)
                Spacer(modifier = Modifier.width(8.dp))
                CircleSkeleton(size = 10.dp)
                Spacer(modifier = Modifier.width(2.dp))
                RoundedSkeleton(width = 50.dp, height = 10.dp, radius = 10.dp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            RoundedSkeleton(width = 150.dp, height = 10.dp, radius = 10.dp)
        }
    }
}

@Preview
@Composable
private fun PreviewChartMain() {
    AksiKampungSiagaCovidTheme {
        ChartMain(
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
private fun PreviewSkeletonChart() {
    AksiKampungSiagaCovidTheme {
        SkeletonChartMain()
    }
}