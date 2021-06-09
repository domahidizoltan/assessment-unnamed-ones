package assignment.ordernotification.notification

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import assignment.ordernotification.subscription.Subscription

class NotificationPublisherTest {

    private val mockRestTemplate = mockk<RestTemplate>()
    private val publisher = NotificationPublisher(mockRestTemplate)

    private val anySubscription = Subscription(
        partnerUrl = "anyUrl",
        apiKey = "anyApiKey"
    )
    private val anyOrderEvent = OrderEvent(
        shop = "shop1",
        eventType = "anyEventType",
        event = "anyEventString"
    )

    @Test
    fun `should return true when orderEvent was published`() {
        val headers = HttpHeaders().apply {
            set("version", "v1")
            set("x-api-key", "anyApiKey")
        }
        val entity = HttpEntity(anyOrderEvent, headers)

        every { mockRestTemplate.postForEntity("anyUrl/orders", entity, Any::class.java) } answers { ResponseEntity.ok().build() }

        val result = publisher.publish(anySubscription, anyOrderEvent)

        assertTrue(result)
    }


    @Test
    fun `should handle failures and return false when failed to publish orderEvent`() {
        every { mockRestTemplate.postForEntity(any<String>(), any(), Any::class.java) } answers { ResponseEntity.badRequest().build() }

        val result = publisher.publish(anySubscription, anyOrderEvent)

        assertFalse(result)
    }

}