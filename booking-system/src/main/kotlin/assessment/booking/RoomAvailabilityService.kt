package assessment.booking

import java.time.Instant
import java.time.Period

class RoomAvailabilityService(private val accommodations: List<Accommodation>, private val reservationService: ReservationService) {

    fun getAvailableRooms(
        timeRange: ClosedRange<Instant> = Instant.now()..Instant.now().plus(Period.ofDays(1)),
        priceRange: ClosedRange<Double>? = null
    ): Map<String, List<Room>> {
        val reservedRooms = reservationService.getReservedRooms(timeRange)

        return accommodations
            .map { it.name to it.rooms.filter(priceRange, reservedRooms[it.name]) }
            .toMap()
    }

    private fun List<Room>.filter(priceRange: ClosedRange<Double>?, reservedRooms: List<String>?) =
       filter { roomIsNotReserved(it.doorNumber, reservedRooms) && it.isWithinPriceRange(priceRange) }

    private fun Room.isWithinPriceRange(priceRange: ClosedRange<Double>?) =
        priceRange == null || price in priceRange

    private fun roomIsNotReserved(doorNumber: String, reservedRooms: List<String>?) =
        reservedRooms != null && doorNumber !in reservedRooms

}
