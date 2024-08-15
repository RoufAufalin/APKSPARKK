package com.data.response

import com.google.gson.annotations.SerializedName

data class SlotResponse(

	@field:SerializedName("slot_selesai")
	val slotSelesai: Int? = null,

	@field:SerializedName("slots_dibooking")
	val slotsDibooking: Int? = null,

	@field:SerializedName("slot_terisi")
	val slotTerisi: Int? = null,

	@field:SerializedName("total_slot")
	val totalSlot: Int? = null,

	@field:SerializedName("slot_kosong")
	val slotKosong: String? = null
)
