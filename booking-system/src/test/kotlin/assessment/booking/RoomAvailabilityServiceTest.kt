package assessment.booking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import assessment.booking.helpers.hotelAstoria
import assessment.booking.helpers.hotelHilton
import assessment.booking.helpers.reservations
import java.time.Instant

class RoomAvailabilityServiceTest {

    private lateinit var roomService: RoomAvailabilityService

    private val accommodations = listOf(hotelHilton, hotelAstoria)

    val timeRange = Instant.parse("2021-03-26T10:00:00Z")..Instant.parse("2021-03-27T10:00:00Z")

    @BeforeEach
    fun setUp() {
        val reservationService = ReservationService(reservations)
        roomService = RoomAvailabilityService(accommodations, reservationService)
    }

    @Test
    fun `should list available rooms by time range`() {
        val actualRooms = roomService.getAvailableRooms(timeRange)

        assertEquals(4, actualRooms.values.flatten().size)
        assertEquals(listOf(20.5, 100.0), actualRooms["Hilton"]?.map { it.price })
        assertEquals(listOf(10.0, 23.45), actualRooms["Astoria"]?.map { it.price })
    }

    @Test
    fun `should get available rooms by time and price range`() {
        val actualRooms = roomService.getAvailableRooms(timeRange, priceRange = 5.0..23.0)

        assertEquals(2, actualRooms.values.flatten().size)
        assertEquals(listOf(20.5), actualRooms["Hilton"]?.map { it.price })
        assertEquals(listOf(10.0), actualRooms["Astoria"]?.map { it.price })
    }

}