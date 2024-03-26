package com.seadev.aksi.model.ui.component.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.ImageUtils

@Composable
fun CardMenuMain(
    onCardClick: (MenuMain) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val (
            distribution, textDistribution, assessment, textAssessment,
            hotline, textHotline, prevention, textPrevention
        ) = createRefs()

        AksiCard(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .constrainAs(distribution) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(assessment.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("1:1")
                },
            onCardClick = { onCardClick.invoke(MenuMain.Distribution) }
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                model = ImageUtils.IconDistribution,
                contentDescription = "Distribution",
                loading = {
                    RoundedSkeleton(
                        modifier = Modifier.shimmer(),
                        width = 48.dp, height = 48.dp, radius = 4.dp
                    )
                },
                error = {
                    Box(modifier = Modifier.size(48.dp))
                }
            )
        }

        AksiCard(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .constrainAs(assessment) {
                    top.linkTo(parent.top)
                    start.linkTo(distribution.end, 8.dp)
                    end.linkTo(hotline.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("1:1")
                },
            onCardClick = { onCardClick.invoke(MenuMain.Assessment) }
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                model = ImageUtils.IconAssessment,
                contentDescription = "Distribution",
                loading = {
                    RoundedSkeleton(
                        modifier = Modifier.shimmer(),
                        width = 48.dp, height = 48.dp, radius = 4.dp
                    )
                },
                error = {
                    Box(modifier = Modifier.size(48.dp))
                }
            )
        }

        AksiCard(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .constrainAs(hotline) {
                    top.linkTo(parent.top)
                    start.linkTo(assessment.end, 8.dp)
                    end.linkTo(prevention.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("1:1")
                },
            onCardClick = { onCardClick.invoke(MenuMain.Hotline) }
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                model = ImageUtils.IconHotline,
                contentDescription = "Distribution",
                loading = {
                    RoundedSkeleton(
                        modifier = Modifier.shimmer(),
                        width = 48.dp, height = 48.dp, radius = 4.dp
                    )
                },
                error = {
                    Box(modifier = Modifier.size(48.dp))
                }
            )
        }
        AksiCard(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .constrainAs(prevention) {
                    top.linkTo(parent.top)
                    start.linkTo(hotline.end, 8.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("1:1")
                },
            onCardClick = { onCardClick.invoke(MenuMain.Prevention) }
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                model = ImageUtils.IconPrevention,
                contentDescription = "Distribution",
                loading = {
                    RoundedSkeleton(
                        modifier = Modifier.shimmer(),
                        width = 48.dp, height = 48.dp, radius = 4.dp
                    )
                },
                error = {
                    Box(modifier = Modifier.size(48.dp))
                }
            )
        }
        Text(
            modifier = Modifier.constrainAs(textDistribution) {
                top.linkTo(distribution.bottom, 4.dp)
                start.linkTo(distribution.start)
                end.linkTo(distribution.end)
            },
            text = "Distribusi",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = AksiColor.Text
        )
        Text(
            modifier = Modifier.constrainAs(textAssessment) {
                top.linkTo(assessment.bottom, 4.dp)
                start.linkTo(assessment.start)
                end.linkTo(assessment.end)
            },
            text = "Penilaian Diri",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = AksiColor.Text
        )
        Text(
            modifier = Modifier.constrainAs(textHotline) {
                top.linkTo(hotline.bottom, 4.dp)
                start.linkTo(hotline.start)
                end.linkTo(hotline.end)
            },
            text = "Hotline",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = AksiColor.Text
        )
        Text(
            modifier = Modifier.constrainAs(textPrevention) {
                top.linkTo(prevention.bottom, 4.dp)
                start.linkTo(prevention.start)
                end.linkTo(prevention.end)
            },
            text = "Pencegahan",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = AksiColor.Text
        )
    }

}
enum class MenuMain{
    Distribution, Assessment, Hotline, Prevention
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardMenuMain() {
    AksiKampungSiagaCovidTheme {
        CardMenuMain{

        }
    }
}