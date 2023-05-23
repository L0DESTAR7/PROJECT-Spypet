# PROJECT-Spypet

This project will help you feed your pet using your phone. 

You can plan its meals from time to the quantity and even track how much it eats.

# Documentation

## Server

Built with : TS, express

The server is the main interface between the mobile app and the raspberry.

The server aquires orders and communicates it 

### Endpoints: 

 Get the list of orders 
````
GET: /orders
````

 Add new order (should be authentificated)
````
POST: /orders
````


 Signup
````
POST: /signup
````

Signin
````
POST: /signin
````


Check if device is registered
````
GET: /registeredDevice
````

Register device
````
POST: /registeredDevice
````

