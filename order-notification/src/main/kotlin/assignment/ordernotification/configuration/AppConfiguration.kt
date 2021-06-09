package assignment.ordernotification.configuration

import io.nats.client.Connection
import io.nats.client.Nats
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@Configuration
@ConfigurationProperties("nats")
data class NatsConfigProps(
    var host: String? = null,
    var port: Int? = null,
    var topic: String? = null
)

@Configuration
class NatsConfiguration {

    @Bean
    fun natsConnection(config: NatsConfigProps): Connection =
        Nats.connect("nats://${config.host}:${config.port}")

}

@Configuration
class AppConfiguration {

    @Bean
    fun restTemplate() = RestTemplate()

}