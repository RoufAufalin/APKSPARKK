package com.data.response

import com.google.gson.annotations.SerializedName

data class SeatStatusResponse(

	@field:SerializedName("Belakang")
	val belakang: List<BelakangItem?>? = null,

	@field:SerializedName("Depan")
	val depan: List<DepanItem?>? = null
)

data class BelakangItem(

	@field:SerializedName("id_blok")
	val idBlok: Int? = null,

	@field:SerializedName("x")
	val x: String? = null,

	@field:SerializedName("y")
	val y: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slot_name")
	val slotName: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DepanItem(

	@field:SerializedName("id_blok")
	val idBlok: Int? = null,

	@field:SerializedName("x")
	val x: String? = null,

	@field:SerializedName("y")
	val y: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slot_name")
	val slotName: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
