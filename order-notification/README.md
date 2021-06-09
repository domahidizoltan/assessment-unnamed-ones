# Order Notification

author: Zoltán Domahidi  
date: April 10, 2021


#### The problem:

#### Description

Your task is to create a service which can notify integration partners about events in our system.

For example: let's say we have a partner called Awesome Reviews. They send requests to the customers to write a review about the products they order. To do this, they'll need to know when a purchase happens, so they subscribe to our `orders` webhook to get notified about all new orders.

##### Requirements:

* Check which partners are subscribed to the event and send them a POST request to the endpoint they provided.
* You can freely choose the message source. We prefer SQS, NSQ or NATS, but you can use other technologies too.
* You can freely choose the database to store the partner related data.
* It should retry failed requests.
* It should handle multiple event types.

##### Some optional extras we would love to see:

* Think about security
* Make it easily extendable and easy to add new events
* Add monitoring for the service, so we can see the partner's API latency and error rate.
* Add a way to reformat messages, e.g. omit, rename properties, or map an event to a new structure

#### Something to think about

When we send large marketing campaigns, there can be spikes in traffic, so a rate-limiting solution would be very helpful. For example, the partners could specify their own our rate limits and we could throttle the requests. However, if one partner hits the rate limit, it shouldn't affect the other partners' performance!

Think about how you would approach this problem and we can discuss it in person :)

---

### Outcome

This was the senior backend software engineer interview assessment of a company in the downtown of Budapest working on an e-commerce product.  
This assessment had a soft time limit of 5 hours.  

Because of the time limit some non-functional features were not implemented. I also had some issues with NATS message consuming in the last hour what was very strange and could not be fixed in short term.  
In the problem description they wrote:
> Don't stress if you can't get every part done -- spend a maximum of 5 hours on this assignment. Feel free to add comments or README notes to help us understand your thinking and decisions. If we like your code overall, we'll discuss the solution in person too so you'll get a chance to tell us what else you wanted to add and how you planned to implement those.

... so I documented these issues to not hide them. However, I failed mainly because of these issues.  

Again, maybe it would be worth to reconsider the time limit, or the amount of problems to solve ;)