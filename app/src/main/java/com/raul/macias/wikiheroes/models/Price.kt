package com.raul.macias.wikiheroes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price (val type : String?, val price: Double) : Parcelable