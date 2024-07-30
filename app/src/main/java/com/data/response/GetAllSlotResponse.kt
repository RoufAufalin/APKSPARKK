package com.data.response

import com.google.gson.annotations.SerializedName

data class SlotParkirResponse(

	@field:SerializedName("total_slot")
	val totalSlot: Int,

	@field:SerializedName("slot_kosong")
	val slotKosong: Int
)
