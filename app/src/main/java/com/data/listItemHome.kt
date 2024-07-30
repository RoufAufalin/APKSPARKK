package com.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class listItemHome(
    val place: String,
    val type: String,
    val location: String
): Parcelable
