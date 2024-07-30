package com.data.retrofit

import com.data.response.BookedResponse
import com.data.response.LoginResponse
import com.data.response.SlotParkirResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("api/slot-parkir")
    suspend fun SlotAll(): SlotParkirResponse

    @FormUrlEncoded
    @POST("api/parkir/booking-slot")
    fun bookSlot(
        @Field("plat_nomor") platNomor: String,
        @Field("nama_pemesan") namaPemesan: String,
        @Field("jenis_mobil") jenisMobil: String,
        @Field("tanggal_masuk") tanggalMasuk: String,
        @Field("tanggal_keluar") tanggalKeluar: String,
        @Field("id_slot") idSlot: String
    ): Call<BookedResponse>

}
