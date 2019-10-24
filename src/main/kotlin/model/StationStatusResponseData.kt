package model

import com.google.gson.annotations.SerializedName

class StationStatusResponseData {
    @SerializedName("stations")
    var stations = ArrayList<StationStatusResponseDataStation>()
}