package model

import com.google.gson.annotations.SerializedName

class StationInfoResponse (
    @SerializedName("data")
    var data: StationInfoResponseData
)