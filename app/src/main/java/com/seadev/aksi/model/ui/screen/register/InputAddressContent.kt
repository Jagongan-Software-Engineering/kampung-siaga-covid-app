package com.seadev.aksi.model.ui.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seadev.aksi.model.domain.model.AksiUser
import com.seadev.aksi.model.ui.theme.AksiColor

@Composable
fun InputAddressContent(
    addressViewModel: AddressViewModel = hiltViewModel(),
    onChangeValid: (isValid: Boolean, user: AksiUser) -> Unit
) {
    val province by addressViewModel.provinces.collectAsStateWithLifecycle()
    val cities by addressViewModel.cities.collectAsStateWithLifecycle()
    val districts by addressViewModel.districts.collectAsStateWithLifecycle()
    val subDistricts by addressViewModel.subDistricts.collectAsStateWithLifecycle()
    val isLoading by addressViewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        addressViewModel.getListProvince()
    }

    var rt by remember { mutableStateOf("") }
    var rw by remember { mutableStateOf("") }
    var fullAddress by remember { mutableStateOf("") }

    var provinceName by remember { mutableStateOf("") }
    var cityName by remember { mutableStateOf("") }
    var districtName by remember { mutableStateOf("") }
    var subDistrictName by remember { mutableStateOf("") }

    val isProvinceValid by remember { derivedStateOf { provinceName.isNotBlank() } }
    val isCityValid by remember { derivedStateOf { cityName.isNotBlank() } }
    val isDistrictValid by remember { derivedStateOf { districtName.isNotBlank() } }
    val isSubDistrictValid by remember { derivedStateOf { subDistrictName.isNotBlank() } }

    val isAddressValid by remember {
        derivedStateOf {
            isProvinceValid && isCityValid && isDistrictValid && isSubDistrictValid && rt.isNotBlank()
                    && rw.isNotBlank() && fullAddress.isNotBlank()
        }
    }
    LaunchedEffect(isAddressValid) {
        onChangeValid(
            isAddressValid, AksiUser.Init.copy(
                provinceId = province.find { p -> p.name == provinceName }?.id ?: "",
                cityId = cities.find { p -> p.name == cityName }?.id ?: "",
                districtId = districts.find { p -> p.name == districtName }?.id ?: "",
                subDistrictId = subDistricts.find { p -> p.name == subDistrictName }?.id ?: "",
                rtNumber = rt,
                rwNumber = rw,
                fullAddress = fullAddress
            )
        )
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Informasi Lokasi",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = AksiColor.Text
            )
        }
        item {
            Text(
                text = "Langkah 2 dari 2",
                color = AksiColor.TextLight
            )
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item {

        }
        item {
            DropDownField(
                modifier = Modifier.fillMaxWidth(),
                title = "Provinsi",
                value = provinceName,
                options = if (isLoading && provinceName.isBlank()) listOf("Loading...") else province.map { it.name }
            ) { text ->
                provinceName = text
                cityName = ""
                districtName = ""
                subDistrictName = ""
                province.find { p -> p.name == text }?.let { addressViewModel.getListCity(it.id) }
            }
        }
        item {
            DropDownField(
                modifier = Modifier.fillMaxWidth(),
                title = "Kabupaten / Kota",
                value = cityName,
                options = if (isLoading && cityName.isBlank()) listOf("Loading...") else cities.map { it.name }
            ) { text ->
                cityName = text
                districtName = ""
                subDistrictName = ""
                cities.find { c -> c.name == text }?.let { addressViewModel.getDistrict(it.id) }
            }
        }
        item {
            DropDownField(
                modifier = Modifier.fillMaxWidth(),
                title = "Kecamatan",
                value = districtName,
                options = if (isLoading && districtName.isBlank()) listOf("Loading...") else districts.map { it.name }
            ) { text ->
                districtName = text
                subDistrictName = ""
                districts.find { d -> d.name == text }
                    ?.let { addressViewModel.getSubDistrict(it.id) }
            }
        }
        item {
            DropDownField(
                modifier = Modifier.fillMaxWidth(),
                title = "Desa / Kelurahan",
                value = subDistrictName,
                options = if (isLoading && subDistrictName.isBlank()) listOf("Loading...") else subDistricts.map { it.name }
            ) { text ->
                subDistrictName = text
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    label = { Text(text = "RT") },
                    value = rt,
                    maxLines = 1,
                    onValueChange = { if (it.length <= 2) rt = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    label = { Text(text = "RW") },
                    value = rw,
                    maxLines = 1,
                    onValueChange = { if (it.length <= 2) rw = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Alamat Lengkap") },
                minLines = 2,
                value = fullAddress,
                onValueChange = {
                    fullAddress = it
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDownField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    options: List<String>,
    onTextSubmit: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            modifier = modifier.menuAnchor(),
            value = value,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(title) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        expanded = false
                        onTextSubmit(option)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }

}