package com.seadev.aksi.model.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AksiSearchTopBar(
    title: String,
    searchPlaceHolder: String, 
    onBack: () -> Unit,
    onSearch: (query: String) -> Unit,
    onClose: () -> Unit
) {
    var showSearch by remember { mutableStateOf(false) }
    var querySearch by remember { mutableStateOf("") }

    MediumTopAppBar(
        navigationIcon = {
            AnimatedVisibility(visible = !showSearch) {
                IconButton(onClick =  onBack) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                }
            }
        },
        title = {
            Column {
                Text(
                    modifier = Modifier,
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                )
            }
        },
        actions = {
            AnimatedVisibility(visible = !showSearch) {
                IconButton(onClick = {
                    showSearch = true
                }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                }
            }
            AnimatedVisibility(visible = showSearch) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 8.dp),
                    query = querySearch,
                    onQueryChange = {
                        querySearch = it
                        onSearch(it)
                    },
                    onSearch = onSearch,
                    colors = SearchBarDefaults.colors(
                        containerColor = AksiColor.Accent
                    ),
                    placeholder = {
                        Text(text = searchPlaceHolder)
                    },
                    leadingIcon = {
                        IconButton(onClick = { showSearch = false }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Close"
                            )
                        }
                    },
                    trailingIcon = {
                        AnimatedVisibility(visible = querySearch.isNotBlank()) {
                            IconButton(onClick = {
                                querySearch = ""
                                onClose()
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    },
                    active = false,
                    onActiveChange = {}) {
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewAksiSearchTopBar() {
    AksiKampungSiagaCovidTheme {
        AksiSearchTopBar(
            "Distribusi Per-Provinsi",
            "Cari Provinsi",
            {}, {}, {}
        )
    }
}