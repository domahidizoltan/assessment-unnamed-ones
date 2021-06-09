package assignment.ordernotification.notification

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import assignment.ordernotification.subscription.SubscriptionRepository
import java.time.Instant

@Service
class NotificationService(
    private val subscriptionRepository: SubscriptionRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationPublisher: NotificationPublisher,
    ) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun sendNotification(orderEvent: OrderEvent) {
        subscriptionRepository.findBySubscribeToShopsContaining(orderEvent.shop)
            .forEach {
                val savedNotification = notificationRepository.save(orderEvent.toNotification())
                if (notificationPublisher.publish(it, orderEvent)) {
                    savedNotification.delivered = Instant.now()
                    notificationRepository.save(savedNotification)
                    logger.info("${savedNotification.eventType} notification with id ${savedNotification.id} delivered to ${it.partnerUrl}")
                } else {
                    logger.warn("${savedNotification.eventType} notification with id ${savedNotification.id} could not be delivered to ${it.partnerUrl}")
                }
            }
    }


    private fun OrderEvent.toNotification() = Notification(
        eventType = eventType,
        shop = shop,
        event = event,
        received = Instant.now()
    )
}