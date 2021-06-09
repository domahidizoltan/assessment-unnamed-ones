package assignment.ordernotification.subscription

import org.springframework.data.repository.CrudRepository

interface SubscriptionRepository: CrudRepository<Subscription, Int> {
    fun findBySubscribeToShopsContaining(shop: String): List<Subscription>
}