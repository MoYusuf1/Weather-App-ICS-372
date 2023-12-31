# Climate Watch
Created By: Jordan Mielke, Mohamed Yusuf, Steve Hartmann

### Problem Statement
> Climate Watch aims to provide accurate and up-to-date weather information. Whether you're 
checking the local weather or planning for a trip abroad, our app ensures you have the latest 
forecasts at your fingertips.

### Product Objective
> Our application offers a comprehensive weather tracking experience, allowing you to effortlessly
view current temperatures at your current location or anywhere of your choosing. Perfect for planning 
your next family vacation, our tool provides an in-depth look at expected highs and lows for any 
destination. Beyond just temperature readings, you'll also gain access to detailed weather insights,
such as wind speeds, humidity levels, visibility, dew point and so much more!

### Functional Components
> * **1. Display Current Weather For City**: Upon launching the app, a pop-up is shown giving the user the ability to
  read current weather data using the user's location.
 
> * **2. Search By ZIP Code**: Enter a valid ZIP code and verify if the weather details are displayed. 
 Test invalid ZIP codes (like "00000" or "Scooby Dooby-Doo") and check for an appropriate error message.
 
> * **3. Unit Conversions**: Access the User Preferences tab, toggle between different units, and observe if the 
  temperature display updates accordingly.

> * **4. Responsive UI Components**: Interact with various UI components to ensure they respond appropriately to user
 input and display relevant weather data efficiently.

> * **5. Location Services**: When loading the app, it will prompt the user with a welcome screen asking if they would
> like to allow for location tracking. If allowed, it will grab the IP address of the user and use that to get their ZIP
> code. If denied, it will default to the ZIP code for Metro State University.

> * **6. Weather Cache**: When searching for ZIP codes to display weather, if you were to check for a previously entered
> ZIP code it will load the cached information instead of doing a REST API call.

### Non-Functional Components
> * **1. JavaFX For GUI**: Our application uses JavaFX to create a user-friendly graphical interface. This ensures
  an intuitive and responsive experience for users interacting with the application.

> * **2. Implements Weather API For Weather Data**: We utilize the OpenWeather API (https://openweathermap.org) to fetch real-time weather data. We've implemented efficient API calls and handled responses to 
  display the data seamlessly within the app.

> * **3. Use IP Geolocation API For Converting User IP Address To Location**: Our application 
  incorporates the IP-Stack API (https://ipstack.com) to convert the user's IP address into a geographical location. This feature 
  enables the app to automatically detect and display weather information for the user's current location, enhancing 
  the app's usability and personalization.

> * **4. Error Handling**: We have implemented comprehensive input validation and error handling mechanisms. This 
  ensures that the application responds gracefully to invalid inputs, such as incorrect ZIP codes, and provides 
  helpful feedback to the user.

### Code Structure & Class Locations
There is a package for each type of category such as caches, controllers, models, etc.

**Four Class Definitions**
  1. `src/main/java/edu/metrostate/model/City.java`
  2. `src/main/java/edu/metrostate/model/FiveDayForecast.java`
  3. `src/main/java/edu/metrostate/model/UserPreferences.java`
  4. `src/main/java/edu/metrostate/model/Weather.java`

**Interface Class** `src/main/java/edu/metrostate/cache/Cache.java`

**Abstract Class** `src/main/java/edu/metrostate/model/Location.java`

### Running the Application
1. Open the MainApp.java class

2. Press the run icon  ![img.png](img.png)

3. Or Right click `src\main\java\edu\metrostate\MainApp.java` and press the run.