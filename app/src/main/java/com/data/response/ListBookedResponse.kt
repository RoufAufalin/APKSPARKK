package com.data.response

import com.google.gson.annotations.SerializedName

data class ListBookedResponse(

	@field:SerializedName("data")
	val data: List<DataItemz>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItemz(

	@field:SerializedName("id_slot")
	val idSlot: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("waktu_booking_berakhir")
	val waktuBookingBerakhir: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("jenis_mobil")
	val jenisMobil: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("waktu_booking")
	val waktuBooking: String? = null,

	@field:SerializedName("plat_nomor")
	val platNomor: String? = null
)
