package assignment.ordernotification.subscription

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Subscription(
    @Id
    @GeneratedValue
    var id: Int? = null,
    val partnerUrl: String,
    val subscribeToShops: String? = null,
    var apiKey: String? = null
)