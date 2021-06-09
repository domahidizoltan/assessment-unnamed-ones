package assessment.booking

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import assessment.booking.helpers.hotelHilton
import assessment.booking.helpers.reservations
import assessment.booking.helpers.userJohnDoe
import java.time.Instant

class ReservationServiceTest {

    private lateinit var reservationService: ReservationService

    val timeRange = Instant.parse("2021-03-26T10:00:00Z")..Instant.parse("2021-03-27T10:00:00Z")
    val copyOfReservations = reservations.toMutableList()

    @BeforeEach
    fun setUp() {
        reservationService = ReservationService(copyOfReservations)
    }

    @Test
    fun `should get reserved rooms within time range`() {
        val reservedRooms = reservationService.getReservedRooms(timeRange)

        val expectedRooms = mapOf("Hilton" to listOf("1/B"), "Astoria" to listOf("3"))
        assertEquals(2, reservedRooms.values.flatten().size)
        assertEquals(expectedRooms, reservedRooms)
    }

    @Test
    fun `should not be able to book reserved room`() {
        val hilton1b = hotelHilton.rooms[1]

        assertThrows<RoomNotAvailableException> {
            reservationService.bookRoom(hotelHilton.name, hilton1b, timeRange, userJohnDoe)
        }
    }

    @Test
    fun `should not be able to book room for less than 24 hours`() {
        val timeRange = Instant.parse("2021-03-26T10:00:00Z")..Instant.parse("2021-03-27T09:59:59Z")
        val hilton1b = hotelHilton.rooms[1]

        assertThrows<InvalidBookingTimeRangeException> {
            reservationService.bookRoom(hotelHilton.name, hilton1b, timeRange, userJohnDoe)
        }
    }

    @Test
    fun `should successfully book a room`() {
        val hilton2 = hotelHilton.rooms[2]

        val reservation = reservationService.bookRoom(hotelHilton.name, hilton2, timeRange, userJohnDoe)

        val expectedReservation = Reservation("Hilton", "2", 100.0, timeRange, userJohnDoe)
        assertEquals(expectedReservation, reservation)
        assertTrue(reservation in reservationService.reservations)
    }

    @Test
    fun `should successfully cancel a booking`() {
        val reservation = reservations[0].copy()

        reservationService.cancelBooking(reservation)

        assertFalse(reservation in reservationService.reservations)
    }

    @Test
    fun `should throw exception on cancel not existing booking`() {
        val reservation = reservations[0].copy(user = userJohnDoe)

        assertThrows<NotExistingReservationException> {
            reservationService.cancelBooking(reservation)
        }
    }

    @Test
    fun `should get bookings of a specific user`() {
        val userBookings = reservationService.getBookings(userJohnDoe)

        assertEquals(2, userBookings.size)
        assertEquals(listOf(copyOfReservations[1], copyOfReservations[2]), userBookings)
    }

}