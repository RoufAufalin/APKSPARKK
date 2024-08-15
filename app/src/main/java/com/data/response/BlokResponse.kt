package com.data.response

import com.google.gson.annotations.SerializedName

data class BlokResponse(

	@field:SerializedName("data")
	val data: List<DataItems>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItems(

	@field:SerializedName("fakultas")
	val fakultas: Fakultas? = null,

	@field:SerializedName("id_fakultas")
	val idFakultas: Int? = null,

	@field:SerializedName("panjang")
	val panjang: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("lebar")
	val lebar: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

)


data class Fakultas(

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
