package com.seadev.aksi.model.ui.component.hotline

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import com.seadev.aksi.model.domain.model.Hotline
import com.seadev.aksi.model.domain.model.Province
import com.seadev.aksi.model.ui.component.AksiCard
import com.seadev.aksi.model.ui.component.CircleSkeleton
import com.seadev.aksi.model.ui.component.RoundedSkeleton
import com.seadev.aksi.model.ui.component.shimmer
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.ImageUtils


@Composable
fun ItemHotline(province: Province, listHotline: List<Hotline>) {
    var isExpand by rememberSaveable { mutableStateOf(false) }
    AksiCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (iconRef, provinceRef, actionRef) = createRefs()
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(iconRef) {
                            top.linkTo(actionRef.top)
                            bottom.linkTo(actionRef.bottom)
                            start.linkTo(parent.start)
                        },
                    model = ImageUtils.IconHotline,
                    contentDescription = "Distribution",
                    loading = {
                        RoundedSkeleton(
                            modifier = Modifier.shimmer(),
                            width = 24.dp, height = 24.dp, radius = 4.dp
                        )
                    },
                    error = {
                        Box(modifier = Modifier.size(24.dp))
                    }
                )
                Text(
                    text = province.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary,
                    modifier = Modifier.constrainAs(provinceRef){
                        top.linkTo(actionRef.top)
                        bottom.linkTo(actionRef.bottom)
                        start.linkTo(iconRef.end, 16.dp)
                        end.linkTo(actionRef.start, 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )
                IconButton(
                    onClick = { isExpand = !isExpand },
                    modifier = Modifier.constrainAs(actionRef){
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                ) {
                    Icon(
                        imageVector = if (isExpand) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Drop Down"
                    )
                }
            }
            AnimatedVisibility(visible = isExpand) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    listHotline.forEach {
                        ItemListHotline(hotline = it)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemListHotline(hotline: Hotline) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                context.startActivity(
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:${hotline.phone}"))
                )
            }
    ) {
        Text(
            text = hotline.hospital,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = hotline.phone)
    }
}

@Composable
fun SkeletonHotline() {
    AksiCard {
        Column(
            modifier = Modifier
                .shimmer()
                .fillMaxWidth()
                .padding(16.dp)
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedSkeleton(width = 32.dp, height = 32.dp, radius = 4.dp)
                Spacer(modifier = Modifier.width(16.dp))
                RoundedSkeleton(width = 150.dp, height = 32.dp)
                Spacer(modifier = Modifier.weight(1f))
                CircleSkeleton(size = 32.dp)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewItemHotline() {
    AksiKampungSiagaCovidTheme {
        ItemHotline(
            province = Province.Indonesia,
            listHotline = listOf(Hotline.Indonesia)
        )
    }
}

@Preview
@Composable
private fun PreviewSkeletonHotline() {
    AksiKampungSiagaCovidTheme {
        SkeletonHotline()
    }

}