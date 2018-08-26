Vicinity  
========  
Vicinity is an Android app that displays venues around user's current location using [Foursquare's Places API](https://developer.foursquare.com/docs/api/endpoints). When user taps a venue some details about selected venue is displayed.  
  
  Architecture  
============  
Vicinity showcases [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) in action. The overall structure of the app is based on [Architecting Android...The clean way?](https://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/) and [Android architecture](http://five.agency/android-architecture-part-1-every-new-beginning-is-hard/)  .
  
How it works  
============  
At the very beginning cache is empty so venues are fetched from web service. After fetching venues from web service, they're persisted to database for later use. By later use I mean  when user opens the app and Internet is not connected or when user's current location is not that much different from his/her previous location (distance between user's previous and current locations is less than 100 meters). When user taps a venue a new Activity is displayed with some details about selected venue. If venue information is present in cache it is returned from cache otherwise a web service is called to obtain venue information.   
  
Libraries  Used
==========
There are a bunch of libraries used in Vicinity. The most important ones are:
- [RxJava](https://github.com/ReactiveX/RxJava)
- Room
- [Dagger](https://github.com/google/dagger)
- [Mosby](https://github.com/sockeqwe/mosby)
- [Retrofit](https://github.com/square/retrofit)

Disclaimer
=======
I've done my best to follow the principles of Clean Architecture. If you find any inconsistency with Clean Architecture or any major issue feel free to open an issue.