This is an inventory app that works through AndroidStudio. It implements google's Firestore database to handle requests for inventory from other users and seperates users
through a unique user ID. It also implements user authentication through email and a password. I will continue to update this file with any different changes made.
1/20/2025:
Set up the basic layout by creating a local database without online functionality
1/21/2025: 
Changed the request portion of the app to use FireBase instead of a local database. 
Implemented User Authentication where a user would need to login through Email and Password.
Fixed a few UI bugs and button errors
TODO: 
Implement the inventory portion that is still using the local database and change it to using FireBase
Setup a way to not only accept a request, but let the request user know how much of the request they can give if not the complete request along with a message
Implement a method where accepting a request and confirming how much inventory items given will subtract from the user's inventory
Change to database defined specific id for user to be shown a username and not a bunch of letters and numbers
