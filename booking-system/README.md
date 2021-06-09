# Booking System

author: Zoltán Domahidi  
date: March 27, 2021


### The problem:

You have to implement a simple accommodation booking system but only for Budapest properties, so you should not take into account other cities.  
There might be several properties available for reservation in Budapest. A user can only reserve a property that is available within a selected time range. The smallest bookable period is one day.  
When in doubt, go for the simplest solution. No interactive behavior is required (e.g.: UI menu or console interpreter). Implement only the business logic behind the user stories and feel free to use static data if needed.  
You should use plain Java 11 or Kotlin for your solution, using frameworks (Spring, Java EE, JavaFX, etc.) is prohibited. Do your best to write quality code!  

Requirements / user stories  
- List available rooms: The user has to be able to list rooms based on price and date/time period)  
- Booking and cancellation: The user has to be able to book a free room and cancel an existing reservation.  
- Booking history (date, period of time, price): The user has to be able to get his booking history with the info about his recent bookings with some attributes such as date, period of time, and the price.  
- Booking history printing: The user has to be able to print his booking history (to the Console).  
- Booking history filters (date, period of time, price): The user has to be able to filter his bookings by the date of the booking and by the price of the booking.  

---

### Outcome

This was the senior backend software engineer interview assessment of a company in the downtown of Budapest. They mainly deliver web and mobile applications for international customers in various domains.  
This assessment had a hard time limit of 3 hours what is very short time in my opinion if you also write tests.  
I failed on this assessment, mainly because the time pressure. I was focusing to deliver the required stories and made some shortcuts, i.e.:
- not using ID-s because there is no database used
- not using BigDecimal for prices
- using map sometimes instead of data classes
- application is not runable and the tests are running only by using IDE  

Maybe it would be worth to reconsider the hard time limit, or some points in the interview process ;)