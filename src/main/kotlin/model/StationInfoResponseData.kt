package model

import com.google.gson.annotations.SerializedName

class StationInfoResponseData {
    @SerializedName("stations")
    var stations = ArrayList<StationInfoResponseDataStation>()
}