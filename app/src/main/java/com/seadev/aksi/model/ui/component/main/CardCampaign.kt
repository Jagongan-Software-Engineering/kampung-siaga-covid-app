package com.seadev.aksi.model.ui.component.main

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
fun CardCampaign(
    onCardClick: () -> Unit
) {
    AksiCard {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (title, desc, btnAction, image) = createRefs()
            Text(
                text = "Mulai penilaian diri sekarang!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )
            SubcomposeAsyncImage(
                model = ImageUtils.IlCampaign,
                contentDescription = "Image Campaign",
                loading = {
                    RoundedSkeleton(
                        modifier = Modifier.shimmer(),
                        width = 100.dp, height = 100.dp, radius = 16.dp
                    )
                },
                error = { Box(modifier = Modifier.size(100.dp))},
                modifier = Modifier.constrainAs(image){
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(parent.end)
                    width = Dimension.value(100.dp)
                }
            )
            Text(
                "Hi, Ayo luangkan waktumu sejenak untuk mengisi penilaian diri agar mengetahui kondisi kamu sekarang!",
                modifier = Modifier.constrainAs(desc){
                    top.linkTo(title.bottom, 4.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(image.bottom)
                    end.linkTo(image.start, 4.dp)
                    width = Dimension.fillToConstraints
                },
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                color = AksiColor.Text,
                fontSize = 12.sp
            )
            Button(
                onClick = onCardClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AksiColor.Secondary
                ),
                modifier = Modifier.constrainAs(btnAction){
                    top.linkTo(image.bottom, 4.dp)
                    start.linkTo(parent.start)
                }
            ) {
                Text(
                    "Mulai Penilaian",
                    color = Color.White
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PreviewCardCampaign() {
    AksiKampungSiagaCovidTheme {
        CardCampaign{

        }
    }
}