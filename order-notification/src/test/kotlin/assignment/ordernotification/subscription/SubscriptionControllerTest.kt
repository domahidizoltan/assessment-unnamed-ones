package assignment.ordernotification.subscription

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest
class SubscriptionControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockkBean
    private lateinit var mockService: SubscriptionService

    @Test
    fun `should subscribe and return api key`() {
        val request = SubscriptionRequest(partnerUrl = "partner1", subscribeToShops = "partner1")
        val subscription = Subscription(partnerUrl = "partner1", subscribeToShops = "partner1")
        val mockedApiKey = "mocked-uuid"
        every { mockService.subscribe(subscription) } answers { subscription.apply { apiKey =  mockedApiKey} }

        val subscribeRequest = post("/subscribe")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))

        mockMvc.perform(subscribeRequest)
            .andExpect(status().isCreated)
            .andExpect(content().json("{'apiKey': '$mockedApiKey'}"))
    }

    @Test
    fun `should return Bad Request when partnerUrl is empty on subscribe`() {
        val subscribeRequest = post("/subscribe")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")

        mockMvc.perform(subscribeRequest)
            .andExpect(status().isBadRequest)
    }
}