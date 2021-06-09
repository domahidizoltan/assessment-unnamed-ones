package assessment.booking

import java.time.Instant

data class User(
    val name: String
)

data class Accommodation(
    val name: String,
    val rooms: List<Room>
)

data class Room(
    val doorNumber: String,
    val price: Double,
)

data class Reservation(
    val accommodationName: String,
    val doorNumber: String,
    val reservedPrice: Double,
    val reservationDateRange: ClosedRange<Instant>,
    val user: User
)