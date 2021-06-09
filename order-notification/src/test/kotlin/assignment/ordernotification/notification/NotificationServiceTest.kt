package assignment.ordernotification.notification

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import assignment.ordernotification.subscription.Subscription
import assignment.ordernotification.subscription.SubscriptionRepository
import java.time.Instant

class NotificationServiceTest {

    private val mockSubscriptionRepository = mockk<SubscriptionRepository>()
    private val mockNotificationRepository = mockk<NotificationRepository>()
    private val mockNotificationPublisher = mockk<NotificationPublisher>()

    private lateinit var service: NotificationService

    private val shop1OrderEvent = OrderEvent(
        shop = "shop1",
        eventType = "anyEventType",
        event = "anyEventString"
    )
    private val shop1Notification = shop1OrderEvent.toNotification()

    @BeforeEach
    fun setUp() {
        service = NotificationService(mockSubscriptionRepository, mockNotificationRepository, mockNotificationPublisher)
    }

    @Test
    fun `should not send notifications when subscription not found`() {
        every { mockSubscriptionRepository.findBySubscribeToShopsContaining(any()) } returns  emptyList()

        service.sendNotification(shop1OrderEvent)

        verify { mockNotificationPublisher wasNot Called }
    }

    @Test
    fun `should send notifications to partner when subscription found`() {
        val partner1Subscription = Subscription(
            id = 1,
            partnerUrl = "partner1Url",
            subscribeToShops = "shop1",
            apiKey = "apiKey1"
        )
        every { mockSubscriptionRepository.findBySubscribeToShopsContaining(any()) } returns  listOf(partner1Subscription)
        every { mockNotificationRepository.save(any()) } returns shop1Notification
        every { mockNotificationPublisher.publish(partner1Subscription, shop1OrderEvent) } returns true

        service.sendNotification(shop1OrderEvent)

        verify { mockNotificationPublisher.publish(partner1Subscription, shop1OrderEvent)}
    }

    @Test
    fun `should send notifications to all subscribed partners`() {
        val partner1Subscription = Subscription(
            id = 1,
            partnerUrl = "partner1Url",
            subscribeToShops = "shop1",
            apiKey = "apiKey1"
        )
        val partner3Subscription = Subscription(
            id = 3,
            partnerUrl = "partner3Url",
            subscribeToShops = "shop1",
            apiKey = "apiKey3"
        )
        every { mockSubscriptionRepository.findBySubscribeToShopsContaining(any()) } returns  listOf(partner1Subscription, partner3Subscription)
        every { mockNotificationRepository.save(any()) } returns shop1Notification
        every { mockNotificationPublisher.publish(partner1Subscription, shop1OrderEvent) } returns true
        every { mockNotificationPublisher.publish(partner3Subscription, shop1OrderEvent) } returns true

        service.sendNotification(shop1OrderEvent)

        verify { mockNotificationPublisher.publish(partner1Subscription, shop1OrderEvent)}
        verify { mockNotificationPublisher.publish(partner3Subscription, shop1OrderEvent)}
    }

    private fun OrderEvent.toNotification() = Notification(
        id = 1,
        eventType = eventType,
        shop = shop,
        event = event,
        received = Instant.now()
    )

}