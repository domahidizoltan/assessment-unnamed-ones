package assignment.ordernotification.subscription

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SubscriptionServiceTest {

    private val mockSubscriptionRepository = mockk<SubscriptionRepository>()

    private val service: SubscriptionService = SubscriptionService(mockSubscriptionRepository)

    @Test
    fun `should save subscription and generate api key`() {
        val subscription = Subscription(partnerUrl = "partner1", subscribeToShops = "partner1")
        every { mockSubscriptionRepository.save(subscription) } returns subscription

        val savedSubscription = service.subscribe(subscription)

        assertTrue(savedSubscription.apiKey!!.isNotBlank())
    }

}