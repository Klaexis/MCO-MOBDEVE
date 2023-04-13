# MCO-MOBDEVE | WalkieCalorie 
 Created By: <br>
 Aleck Jasper Lim <br>
 Abrience Luigi Liao <br>
<br>

## Description
The application **WalkieCalorie** is a fitness measurement app. Users are capable of registering and creating an account on the app. Once the user has an account he/she is eligible to track progress of how much distance they have walked/jogged, the amount of time they have done the activity, and an estimated counter of how much calories they have burned. The calories burned will be calculated with the formula: [Minutes * (METS X 3.5 X Body Weight (KG)) / 200]. The user can turn on the tracker and it will start tracking how much distance and time the user has traveled which will be used to calculate the amount of calories burned per minute.  A progress tracker will also be placed to check how many calories the user has burned in total and how much distance and time the user has traveled. When the tracker has been stopped then the progress will be then placed inside the progress tracker.

## Services / APIs
- **Local Database (SQLite)** <br>
  - To store / verify user credentials <br>
  - To store the progress of the user <br>
  - To store map coordinates <br>
- **Geolocation | Google Maps API**
  - Used to display and track current location <br>
  - Used to track movement of users on how much distance traveled. <br>

## Functions / Features
- **Register** <br>
  - The user must first register an account before accessing the other features of the app. Users are required to give their full name, email, password, weight. <br>
- **Log-in** <br>
  - The user must log-in before using the application’s features. This requires the user to enter his/her registered email and a password. <br>
- **Profile Page** <br>
  - The user will be able to see his/her credentials and has access to other features. <br>
- **Google Maps and Movement Tracker** <br>
  - The user will use google maps to determine the current location. There will be a need to use the Google Maps API and track total movement of the user throughout the session when the tracker is started. <br>
- **Progress Tracker** <br>
  - The user will see their progress based on the distance they traveled and amount of time that has passed. This will display the user’s total distance traveled, amount of calories burned for the set amount of time. The calories burned for the set amount of time will be calculated with the formula given from the Calories Calculator. The tracker will be updated everytime the user has stopped the tracker when it has been started. <br>
- **Calories Calculator** <br>
  - The user will be able to calculate their estimated calories burned through a formula that will be used: [Minutes * (METS X 3.5 X Body Weight (KG)) / 200]. This requires the users to input the estimated time of the activity, the activity the user will be doing (This is for the METS value in which Walking = 3.5 METS, Jogging = 7.0 METS), and the current body weight of the user. <br>

## Contents of MOBDEVE MP
- [`Activity`](app/src/main/java/com/mobdeve/s11/group15/mco/Activity) contains all the Activity.kt files.
- [`Database`](app/src/main/java/com/mobdeve/s11/group15/mco/Database) contains the DatabaseHelper and the model schema of each database.
- [`Adapter`](app/src/main/java/com/mobdeve/s11/group15/mco/Adapter) contains the Adapter and ViewHolder .kt files. 
- [`Layout`](app/src/main/res//layout) contains all the .xml files for the Views.
- [`Drawable`](app/src/main/res//drawable) contains all the vector and image assets.
