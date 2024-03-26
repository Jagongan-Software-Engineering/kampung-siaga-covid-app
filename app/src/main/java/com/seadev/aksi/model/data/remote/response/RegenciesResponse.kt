package com.seadev.aksi.model.data.remote.response

import com.google.gson.annotations.SerializedName
import com.seadev.aksi.model.domain.model.City
import com.seadev.aksi.model.domain.model.District
import com.seadev.aksi.model.domain.model.SubDistrict

data class AddressResponse<T>(
	@field:SerializedName("RECORDS")
	val data: List<T>? = null
)

data class RegencyResponse(
	@field:SerializedName("province_id")
	val provinceId: String? = null,
	@field:SerializedName("name")
	val name: String? = null,
	@field:SerializedName("id")
	val id: String? = null
)

fun RegencyResponse.toData() = City(
	name = this.name?:"",
	provinceId = this.provinceId?:"",
	id = this.id?:""
)

data class DistrictResponse(
	@field:SerializedName("regency_id")
	val regencyId: String? = null,
	@field:SerializedName("name")
	val name: String? = null,
	@field:SerializedName("id")
	val id: String? = null
)

fun DistrictResponse.toData() = District(
	name = this.name?:"",
	id = this.id?:"",
	cityId = this.regencyId?:""
)

data class SubDistrictResponse(
	@field:SerializedName("district_id")
	val districtId: String? = null,
	@field:SerializedName("name")
	val name: String? = null,
	@field:SerializedName("id")
	val id: String? = null
)

fun SubDistrictResponse.toData() = SubDistrict(
	name = this.name?:"",
	id = this.id?:"",
	districtId = this.districtId?:""
)

