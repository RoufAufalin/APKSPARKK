package com.data.response

import com.google.gson.annotations.SerializedName

data class BookedResponse(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("id_slot")
	val idSlot: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("waktu_booking_berakhir")
	val waktuBookingBerakhir: String? = null,

	@field:SerializedName("nama_pemesan")
	val namaPemesan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("jenis_mobil")
	val jenisMobil: String? = null,

	@field:SerializedName("waktu_booking")
	val waktuBooking: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("plat_nomor")
	val platNomor: String? = null
)
