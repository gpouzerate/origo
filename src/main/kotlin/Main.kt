import service.BikeService
import kotlin.system.exitProcess

fun main() {
    val bikeService = BikeService()
    bikeService.getStationsInfo(successCb = { stationsInfo ->
        bikeService.getStationsStatus(successCb = { stationsStatus ->
            for (station in stationsInfo) {
                val stationStatus = stationsStatus[station.key]
                if (stationStatus != null)
                    println("Station: ${station.value.name}, available docks: ${stationStatus.numDocksAvailable}, available bikes: ${stationStatus.numBikesAvailable}")
                else
                    println("Station: ${station.value.name}, status not available")
            }
            exitProcess(0)
        }, errorCb = { reason ->
            println("Error getting stations status: $reason")
            exitProcess(-1)
        })
    }, errorCb = { reason ->
        println("Error getting stations info: $reason")
        exitProcess(-1)
    })
}
