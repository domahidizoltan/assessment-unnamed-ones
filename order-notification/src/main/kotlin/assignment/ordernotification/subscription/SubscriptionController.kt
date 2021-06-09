package assignment.ordernotification.subscription

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SubscriptionController(private val subscriptionService: SubscriptionService) {

    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    fun subscribe(@RequestBody request: SubscriptionRequest): SubscriptionResponse {
        val subscription = Subscription(
            partnerUrl = request.partnerUrl,
            subscribeToShops = request.subscribeToShops
        )

        val persistedSubscription = subscriptionService.subscribe(subscription)
        return SubscriptionResponse(apiKey = persistedSubscription.apiKey ?: "")
    }

}