package com.seadev.aksi.model.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seadev.aksi.model.ui.theme.AksiColor

@Composable
fun AksiCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = AksiColor.Background
        ),
        content = content,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AksiCard(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = AksiColor.Background
        ),
        content = content,
        onClick = onCardClick,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        )
    )
}