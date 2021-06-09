package assignment.ordernotification.notification

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class OrderEvent(
    var eventType: String,
    var shop: String,
    var event: String
)

@Entity
data class Notification(
    @Id
    @GeneratedValue
    var id: Int? = null,

    val eventType: String,
    val shop: String,
    val event: String,
    val received: Instant,
    var delivered: Instant? = null
)