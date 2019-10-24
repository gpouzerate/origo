package service

import model.StationInfoResponse
import model.StationInfoResponseDataStation
import model.StationStatusResponse
import model.StationStatusResponseDataStation
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit.BikeInterface

const val serviceBaseUrl = "https://gbfs.urbansharing.com/oslobysykkel.no/"

class BikeService {
    var bikeItf: BikeInterface

    init {
        val client = OkHttpClient().newBuilder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(serviceBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        bikeItf = retrofit.create(BikeInterface::class.java)
    }

    fun getStationsInfo(successCb: (stations: MutableMap<String, StationInfoResponseDataStation>) -> Unit, errorCb: (reason: String) -> Unit) {
        bikeItf.stationInformation().enqueue(object : Callback<StationInfoResponse> {
            override fun onResponse(systemInfo: Call<StationInfoResponse>, response: Response<StationInfoResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val stationsInfo: MutableMap<String, StationInfoResponseDataStation> = hashMapOf()
                        for (station : StationInfoResponseDataStation in body.data.stations) {
                            if (station.id != null)
                                stationsInfo[station.id] = station
                        }
                        successCb(stationsInfo)
                    } else {
                        errorCb("Response body was null.")
                    }
                } else {
                    errorCb("Response was not successful.")
                }
            }

            override fun onFailure(call: Call<StationInfoResponse>, t: Throwable) {
                errorCb(t.localizedMessage)
            }
        })
    }

    fun getStationsStatus(successCb: (stations: MutableMap<String, StationStatusResponseDataStation>) -> Unit, errorCb: (reason: String) -> Unit) {
        bikeItf.stationStatus().enqueue(object : Callback<StationStatusResponse> {
            override fun onResponse(stationStatus: Call<StationStatusResponse>, response: Response<StationStatusResponse>) {
                if (response.isSuccessful) {
                    val body: StationStatusResponse? = response.body();
                    if (body != null) {
                        val stationsStatus: MutableMap<String, StationStatusResponseDataStation> = hashMapOf()
                        for (station in body.data.stations) {
                            if (station.id != null)
                                stationsStatus[station.id] = station
                        }
                        successCb(stationsStatus)
                    } else {
                        errorCb("Response body was null.")
                    }
                } else {
                    errorCb("Response was not successful.")
                }
            }

            override fun onFailure(call: Call<StationStatusResponse>, t: Throwable) {
                errorCb(t.localizedMessage)
            }
        })
    }
}