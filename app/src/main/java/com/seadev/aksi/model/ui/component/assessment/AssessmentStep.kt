package com.seadev.aksi.model.ui.component.assessment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.utils.DataUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentStep(
    currentIndex: Int = -1,
    stepQuestion: DataUtils.SelfAssessmentQuestion.Question,
    onSelectedIndexChange: (index: Int) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(currentIndex) }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = stepQuestion.title,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterChip(modifier = Modifier
                .weight(1f)
                .height(48.dp),
                selected = selectedIndex == 0,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AksiColor.Accent
                ),
                onClick = {
                    selectedIndex = 0
                    onSelectedIndexChange.invoke(selectedIndex)
                },
                trailingIcon = if (selectedIndex == 0) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null,
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(if (selectedIndex == 0) 0.8f else 1f),
                        text = "Tidak",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                })
            FilterChip(modifier = Modifier
                .weight(1f)
                .height(48.dp),
                selected = selectedIndex == 1,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AksiColor.Accent
                ),
                onClick = {
                    selectedIndex = 1
                    onSelectedIndexChange.invoke(selectedIndex)
                },
                trailingIcon = if (selectedIndex == 1) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null,
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(if (selectedIndex == 1) 0.8f else 1f),
                        text = "Ya",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                })
        }
    }
}