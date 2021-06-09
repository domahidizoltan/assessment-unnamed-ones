package assessment.booking.helpers

import assessment.booking.Accommodation
import assessment.booking.Reservation
import assessment.booking.Room
import assessment.booking.User
import java.time.Instant

val userJohnDoe = User(name = "John Doe")
val userJaneDoe = User(name = "Jane Doe")

val hotelHilton = Accommodation(name = "Hilton",
    rooms = listOf(
        Room(doorNumber = "1", price = 20.5),
        Room(doorNumber = "1/B", price = 2.1),
        Room(doorNumber = "2", price = 100.0),
    )
)
val hotelAstoria = Accommodation(name = "Astoria",
    rooms = listOf(
        Room(doorNumber = "1", price = 10.0),
        Room(doorNumber = "2", price = 23.45),
        Room(doorNumber = "3", price = 99.9),
    )
)

val reservations = mutableListOf(
    Reservation("Hilton", "1/B", 1.9, user = userJaneDoe,
        reservationDateRange = Instant.parse("2021-03-25T10:00:00Z")..Instant.parse("2021-03-26T14:00:00Z")),
    Reservation("Astoria", "2", 24.45, user = userJohnDoe,
        reservationDateRange = Instant.parse("2021-01-20T10:00:00Z")..Instant.parse("2021-03-21T14:00:00Z")),
    Reservation("Astoria", "3", 89.9, user = userJohnDoe,
        reservationDateRange = Instant.parse("2021-01-22T10:00:00Z")..Instant.parse("2021-03-29T15:00:00Z"))
)
