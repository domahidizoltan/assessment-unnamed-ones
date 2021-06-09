package assignment.ordernotification.subscription

class SubscriptionRequest(
    val partnerUrl: String,
    var subscribeToShops: String? = null,
)

class SubscriptionResponse(
    val apiKey: String
)