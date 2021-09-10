# Courier and Store Management System

Used Java 11.0.12, Spring Boot 2.5.4, MySQL Community Server 8.0.26 (sql files in the demo path)

This projects main goal is courier management and navigating.

## Courier Management

Courier has two actions: "Go Store" and "Go Customer"

If circumferences are okay, the courier is goes to store then goes to random customer location (in specified range).
    
Courier latitudes and longtitudes type are BigDecimal for lower memory usage and time type is LocalDateTime.

The distances of two coordinate are calculating with Haversine Formula.

Also querying total distance for every courier. 


### Haversina Formula
<img width="439" alt="27240436-e9a459da-52d4-11e7-8f84-f96d0b312859" src="https://user-images.githubusercontent.com/38990648/132893015-2b9c154b-9a9a-43dc-a5f9-1adff6a334cf.png">

## Courier API

Courier Controller has six request type:

CRUD Operations, Get Total Distance and Navigate Courier.

You can request these easily with SwaggerUI but I still add the main request.

### http://localhost:8080/api/couriers/navigate/2029-09-06T18:43:20?selectCourier=2&&courierLat=40.99233070&&courierLgt=29.12447220

This requests parameters are time, lat, lgt, courierId. When you request it, courier goes to store and then random customer if circumferences are okay. Then automatically adds distance to total distance.

## Store Management

Store management has only CRUD Operations cause I just need CRUD.

## Custom Exception Handling

This project has two custom exception. CourierNotFoundException and StoreNotFoundException.

Their dependency are managing from Spring. (Used @ControllerAdvice)

## Tests

I wrote unit tests for Rest Requests and Mathematic Service (%58 Coverage) usin Mockito and JUnit.

## Design Patterns
Which design patterns i used?

Builder: Lombok automatically prepares builder with just @Builder annotation. I used it for exception response and tests.

Singleton: SwaggerConfig, Entity, Controller, Repository etc. All springs beans scope is default singleton.

IoC: Spring IoC Container has IoC Pattern. IoC Container is managing beans and their dependencies.




















