package com.data.response

import com.google.gson.annotations.SerializedName

data class FakultasResponse(

    @field:SerializedName("data")
	val data: List<DataItem?>? = null,

    @field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
)
