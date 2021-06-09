package assignment.ordernotification.subscription

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SubscriptionService(private val subscriptionRepository: SubscriptionRepository) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun subscribe(subscription: Subscription): Subscription {
        subscription.apiKey = UUID.randomUUID().toString()

        return subscriptionRepository.save(subscription)
            .also { logger.info("${subscription.partnerUrl} subscribed") }
    }

}