package assignment.ordernotification.notification

import org.springframework.data.repository.CrudRepository

interface NotificationRepository: CrudRepository<Notification, Int>