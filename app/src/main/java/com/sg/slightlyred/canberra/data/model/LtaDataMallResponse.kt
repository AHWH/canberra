package com.sg.slightlyred.canberra.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LtaDataMallResponse<T>(
    @Json(name = "odata.metadata")
    var metadata: String,
    @Json(name = "value")
    var values: List<T>
)