package com.seadev.aksi.model.ui.component.distribution

import android.content.res.Configuration
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seadev.aksi.model.domain.model.DistributionCase
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.CircleSkeleton
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.formatCaseNumber

@Composable
fun ItemDistribution(
    case: DistributionCase
) {
    AksiCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = case.province,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = AksiColor.Text
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(AksiColor.Yellow)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Total Kasus Positif",
                            color = AksiColor.TextLight,
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 22.dp),
                        text = case.positive.formatCaseNumber(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AksiColor.Text
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(AksiColor.Green)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sembuh",
                            color = AksiColor.TextLight,
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 22.dp),
                        text = case.recover.formatCaseNumber(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AksiColor.Text
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(AksiColor.Red)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Meninggal",
                            color = AksiColor.TextLight,
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 22.dp),
                        text = case.died.formatCaseNumber(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AksiColor.Text
                    )
                }
            }
        }
    }
}

@Composable
fun SkeletonItemDistribution() {
    AksiCard(
        modifier = Modifier.shimmer()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            RoundedSkeleton(
                width = 250.dp,
                height = 24.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircleSkeleton(size = 14.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        RoundedSkeleton(width = 120.dp, height = 12.dp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    RoundedSkeleton(
                        modifier = Modifier.padding(start = 22.dp),
                        width = 80.dp, height = 18.dp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircleSkeleton(size = 14.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        RoundedSkeleton(width = 60.dp, height = 12.dp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    RoundedSkeleton(
                        modifier = Modifier.padding(start = 22.dp),
                        width = 80.dp, height = 18.dp
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircleSkeleton(size = 14.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        RoundedSkeleton(width = 60.dp, height = 12.dp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    RoundedSkeleton(
                        modifier = Modifier.padding(start = 22.dp),
                        width = 80.dp, height = 18.dp
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PreviewItemDistribution() {
    AksiKampungSiagaCovidTheme {
        ItemDistribution(
            case = DistributionCase.Init.copy(
                province = "DKI JAKARTA",
                positive = 1568040,
                recover = 1551523,
                died = 16098
            )
        )
    }
}

@Preview
@Composable
private fun PreviewSkeletonItemDistribution() {
    AksiKampungSiagaCovidTheme {
        SkeletonItemDistribution()
    }
}