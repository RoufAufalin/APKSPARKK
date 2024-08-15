package com.data.retrofit

import com.data.response.BookedResponse
import com.data.response.LoginResponse
import com.data.response.SlotParkirResponse
import com.data.response.SlotResponse
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
    suspend fun SlotAll(): SlotResponse

    @FormUrlEncoded
    @POST("api/parkir/booking-slot")
    fun bookSlot(
        @Field("plat_nomor") platNomor: String,
        @Field("nama_pemesan") namaPemesan: String,
        @Field("jenis_mobil") jenisMobil: String,
        @Field("id_slot") idSlot: String
    ): Call<BookedResponse>

    @FormUrlEncoded
    @POST("/api/register")
    suspend fun register(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") alamat: String,
        @Field("phone") noHp: String
    ): LoginResponse



}
