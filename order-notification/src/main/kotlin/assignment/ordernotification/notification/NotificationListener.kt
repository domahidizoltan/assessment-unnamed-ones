package assignment.ordernotification.notification

import com.fasterxml.jackson.databind.ObjectMapper
import io.nats.client.Connection
import io.nats.client.Subscription
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.time.Duration
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Component
class NotificationListener(
    private val natsConnection: Connection,
    @Value("\${nats.topic}") private val topic: String,
    private val objectMapper: ObjectMapper,
    private val notificationService: NotificationService
    ) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private lateinit var subscription: Subscription

    @PostConstruct
    fun init() {
        subscription = natsConnection.subscribe(topic)
        logger.info("Subscribed to NATS topic $topic")
        listen()
    }

    @PreDestroy
    fun cleanUp() {
        subscription.unsubscribe()
        logger.info("Unsubscribed from NATS topic $topic")
    }

    fun listen() {
        while(true) {
            val msg = subscription.nextMessage(Duration.ZERO)
            try {
                val orderEvent = objectMapper.readValue(String(msg.data), OrderEvent::class.java)
                logger.info("Received orderEvent $orderEvent")
                notificationService.sendNotification(orderEvent)
            } catch (ex: RuntimeException) {
                logger.error("Could not publish notification", ex)
            }
        }
    }
}