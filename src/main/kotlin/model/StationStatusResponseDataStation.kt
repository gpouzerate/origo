package model

import com.google.gson.annotations.SerializedName

class StationStatusResponseDataStation {
    @SerializedName("station_id")
    val id: String? = null
    @SerializedName("num_bikes_available")
    val numBikesAvailable = 0
    @SerializedName("num_docks_available")
    val numDocksAvailable = 0
}