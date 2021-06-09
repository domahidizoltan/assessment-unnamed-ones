package assignment.ordernotification.notification

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import assignment.ordernotification.subscription.Subscription

private const val ORDERS_ENDPOINT = "/orders"
private const val VERSION = "v1"

@Component
class NotificationPublisher(private val restTemplate: RestTemplate) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun publish(subscription: Subscription, orderEvent: OrderEvent): Boolean {
        val url = subscription.partnerUrl + ORDERS_ENDPOINT
        val headers = buildHeaders(subscription)
        val entity = HttpEntity(orderEvent, headers)

        return try {
            val response = restTemplate.postForEntity(url, entity, Any::class.java)
            response.statusCode == HttpStatus.OK
        } catch (ex: RestClientException) {
            logger.error("Could not deliver order ${orderEvent.eventType} of ${orderEvent.shop} to ${subscription.partnerUrl}", ex)
            false
        }
    }

    private fun buildHeaders(subscription: Subscription) = HttpHeaders().apply {
        set("version", VERSION)
        set("x-api-key", subscription.apiKey)
    }

}