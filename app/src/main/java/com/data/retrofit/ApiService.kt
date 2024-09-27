package com.data.retrofit

import com.data.response.BlokResponse
import com.data.response.BookedResponse
import com.data.response.ListBookedResponse
import com.data.response.LoginResponse
import com.data.response.SeatStatusResponse
import com.data.response.SlotParkirResponse
import com.data.response.SlotResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("api/slot-parkir/{id}/get-total-slot-parkirs")
    suspend fun getALlSlot(
        @Path("id") id: Int
    ): SlotResponse

    @GET("api/slot-parkir/{id}/get-total-data-slot")
    suspend fun getBlokTotal(
        @Path("id")id: Int
    ) : BlokResponse

    @GET("api/slot-parkir/{id}/get-slot-depan-belakang")
    suspend fun getStatusDepanBelakang(
        @Path("id")id: Int
    ) : SeatStatusResponse

    @FormUrlEncoded
    @POST("api/parkir/booking-slot")
    suspend fun bookSlot(
        @Field("id_user") idUSer: Int,
        @Field("plat_nomor") platNomor: String,
        @Field("jenis_mobil") jenisMobil: String,
        @Field("id_slot") idSlot: String
    ): BookedResponse

    @FormUrlEncoded
    @POST("/api/register")
    suspend fun register(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") alamat: String,
        @Field("phone") noHp: String
    ): LoginResponse


    @GET("/api/parkir/get-data-pesanan/{id}")
    suspend fun getListBook(
        @Path("id") id: Int
    ) : ListBookedResponse

}
