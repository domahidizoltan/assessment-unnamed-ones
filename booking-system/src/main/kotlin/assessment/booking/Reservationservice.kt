package assessment.booking

import java.lang.RuntimeException
import java.time.Instant
import java.time.temporal.ChronoUnit

class RoomNotAvailableException: RuntimeException()
class InvalidBookingTimeRangeException: RuntimeException()
class NotExistingReservationException: RuntimeException()

class ReservationService(val reservations: MutableList<Reservation>) {

    fun getReservedRooms(timeRange: ClosedRange<Instant>): Map<String, List<String>> =
        reservations
            .filter { it.reservationDateRange.hasOverlapWith(timeRange) }
            .groupBy { it.accommodationName }
            .mapValues { it.value.map { res -> res.doorNumber } }

    fun bookRoom(accommodationName: String, room: Room, timeRange: ClosedRange<Instant>, user: User): Reservation {
        validateReservation(accommodationName, room, timeRange, user)

        return Reservation(
            accommodationName = accommodationName,
            doorNumber = room.doorNumber,
            reservedPrice = room.price,
            reservationDateRange = timeRange,
            user = user
        ).also {
            reservations.add(it)
        }
    }

    fun cancelBooking(reservation: Reservation) {
        if (reservation !in reservations) throw NotExistingReservationException()

        reservations.remove(reservation)
    }

    fun getBookings(user: User) =
        reservations.filter { it.user == user }

    private fun validateReservation(accommodationName: String, room: Room, timeRange: ClosedRange<Instant>, user: User) {
        if (timeRange.isLessThan24Hours()) throw InvalidBookingTimeRangeException()

        val reservedAccommodationRooms = getReservedRooms(timeRange).getOrDefault(accommodationName, listOf())

        if (room.doorNumber in reservedAccommodationRooms) throw RoomNotAvailableException()

    }

    private fun ClosedRange<Instant>.isLessThan24Hours() =
        start.plus(24, ChronoUnit.HOURS) > endInclusive

    private fun ClosedRange<Instant>.hasOverlapWith(currentRange: ClosedRange<Instant>) =
        currentRange.start in this || currentRange.endInclusive in this
}
