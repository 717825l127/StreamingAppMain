Console-Based Video Streaming Application Using Java OOP

1. Problem Statement

Design and implement a console-based Video Streaming Application using Object-Oriented Programming (OOP) concepts in Java. The application manages users, subscription plans, a content catalog containing movies and series, watchlists, and viewing history.

The system allows users to subscribe to streaming plans, watch movies and series, maintain watchlists, and receive personalized recommendations. It also generates reports displaying the top-watched content and plan-wise monthly revenue.

The project demonstrates inheritance, method overloading, method overriding, polymorphism, and encapsulation through a practical Java application.

2. Objectives

* Manage users and their subscription details.
* Create and manage subscription plans.
* Maintain a catalog of movies and series.
* Allow users to create watchlists and record viewing history.
* Implement different playback behaviors for movies and series.
* Generate recommendations based on genre, year, and rating.
* Provide personalized recommendations based on viewing history.
* Display top-watched content and plan-wise revenue.
* Demonstrate core Object-Oriented Programming concepts in Java.

3. Technologies Used

* Programming Language: Java
* Java Collections Framework: ArrayList, HashMap, List, and Map
* Java Stream API: Filtering, sorting, grouping, and recommendations
* Development Environment: Eclipse IDE
* Application Type: Console-Based Application
* Version Control: Git and GitHub

4. Project Features

User Management

* Create users with unique user IDs, names, and email addresses.
* Assign subscription plans to users.
* Change subscription plans while updating subscriber counts.
* Maintain individual watchlists and viewing histories.

Subscription Plan Management

* Support Basic, Standard, and Premium subscription plans.
* Store plan name, monthly price, maximum screens, and streaming quality.
* Calculate revenue based on the number of subscribers.

Content Management

* Maintain a content catalog of movies and series.
* Store content ID, title, genre, release year, and rating.
* Track how many times each item has been watched.
* Validate ratings to ensure they remain between 0 and 10.

Playback Management

* Play movies with their duration displayed.
* Play the next episode of a series for each user.
* Record successfully played content in the user’s viewing history.
* Display streaming quality according to the user’s active plan.

Recommendation System

* Recommend content by genre.
* Recommend content released from a specified year onward.
* Recommend content meeting a minimum rating.
* Generate personalized recommendations using the user’s viewing history.

Reports

* Display the top three most-watched content items.
* Calculate monthly revenue for each subscription plan.
* Display total monthly subscription revenue.

5. Classes and Responsibilities

Plan

Stores subscription plan information, including plan ID, name, monthly price, screen limit, and streaming quality. It also maintains subscriber counts and calculates revenue.

Content

Acts as the parent class for movies and series. Stores content information, ratings, and watch counts. Provides rating validation and common playback functionality.

Movie

Extends the Content class and overrides the play() method to display movie playback details, including the movie title and duration.

Series

Extends the Content class and overrides the play() method to play the next episode for each user. Episode progress is maintained using a map.

User

Stores user details, the active subscription plan, watchlist, and viewing history. Provides methods to subscribe to plans, add content to a watchlist, and record viewing history.

StreamingService

Manages users, subscription plans, and the content catalog. Handles playback, recommendations, top-watched reports, and plan-wise revenue calculations.

StreamingAppMain

The main class that creates sample plans, users, movies, and series. It tests subscription management, playback, validation, recommendations, and reports.

6. Object-Oriented Programming Concepts

Inheritance

Movie and Series extend the Content class and reuse its common attributes and methods.

Method Overloading

The recommend() method is overloaded to support three recommendation criteria:

* Genre: recommend(User, String)
* Release year: recommend(User, int)
* Minimum rating: recommend(User, double)

Method Overriding

Movie and Series override the play(User) method to implement different playback behaviors.

Polymorphism

The application stores movies and series using Content references and processes them through List. The appropriate overridden play() method executes at runtime.

Encapsulation

Private fields protect the internal state of users, plans, and content. Validation prevents invalid ratings, non-positive plan prices, and null subscription plans.

7. Project Structure

VideoStreamingApp/
├── src/
│   ├── Plan.java
│   ├── Content.java
│   ├── Movie.java
│   ├── Series.java
│   ├── User.java
│   ├── StreamingService.java
│   └── StreamingAppMain.java
└── README.md

Note: In the current implementation, all classes are contained in a single source file named StreamingAppMain.java. The structure above illustrates a possible organization if the classes are separated into individual files. You can keep the existing single-file structure.

8. Prerequisites

* Java Development Kit (JDK 8 or later).
* Eclipse IDE or another Java-compatible editor.
* A terminal or command prompt for command-line execution.

Verify Java installation using:

java -version
javac -version

9. How to Compile and Run

Method 1: Using Eclipse IDE

1. Open Eclipse IDE.
2. Create or open a Java project.
3. Place StreamingAppMain.java inside the project’s source folder.
4. Ensure the file contains the public class StreamingAppMain.
5. Right-click the file.
6. Select Run As → Java Application.
7. View the program output in the Eclipse Console.

Method 2: Using the Command Line

If StreamingAppMain.java contains all classes and has no package declaration, navigate to its directory and execute:

Compile:

javac StreamingAppMain.java

Run:

java StreamingAppMain

If the source file is inside a directory such as src, navigate to that directory before running these commands, or use the appropriate source path.

10. Sample Output

The following output is based on the project’s demonstrated execution.

=== Playback ===
Asha is watching movie 'Inception' (148 min) in 4K
Asha is watching 'Dark' episode 1/26 in 4K
Asha is watching 'Dark' episode 2/26 in 4K
Ravi is watching movie 'The Hangover' (100 min) in HD
Ravi is watching 'Friends' episode 1/236 in HD
Meena is watching 'Friends' episode 1/236 in HD
Guarded: Rating must be between 0 and 10
Guarded: Plan cannot be null
=== Top 3 Watched ===
Dark - 2 views
Friends - 2 views
Inception - 1 views
=== Plan-wise Revenue ===
Basic      subscribers=0 revenue=Rs.0.00
Standard   subscribers=2 revenue=Rs.998.00
Premium    subscribers=1 revenue=Rs.799.00
Total monthly revenue: Rs.1797.00
=== Recommendations for Asha ===
By genre (Comedy): [The Hangover, Friends]
By year (>=2015): [Money Heist]
By rating (>=8.5): [Interstellar, Friends]
Personalized: [Interstellar]

Note: The actual output includes content details such as genre, year, and rating in recommendation lists. The sample above is shortened for readability.

11. Expected Results

The application successfully demonstrates the management of users, plans, movies, series, watchlists, and viewing history. It provides different playback behaviors for movies and series, validates plan and rating updates, and generates recommendation and revenue reports.

The console output confirms that the sample execution records content views, calculates subscription revenue, and displays personalized recommendations.

12. Limitations

* Content playback is simulated through console messages; actual video streaming is not implemented.
* Data is stored in memory and is not retained after the application terminates.
* Subscription revenue represents calculated monthly subscription value rather than verified payment transactions.
* Recommendations use basic filtering and viewing-history logic.

13. Future Enhancements

* Integrate a database such as MySQL to store users and viewing history.
* Implement user authentication.
* Develop a graphical user interface or web interface.
* Add real video streaming capabilities.
* Introduce advanced recommendation algorithms.
* Integrate payment processing and subscription renewal.

14. Student Details

* Student Name: KAVIYA V
* Roll Number: 717825L127
* Project Title: Console-Based Video Streaming Application
* Programming Language: Java
* Development Environment: Eclipse IDE

15. Conclusion

The Console-Based Video Streaming Application demonstrates the practical implementation of Object-Oriented Programming concepts in Java. It provides basic user and subscription management, content playback simulation, watchlist management, viewing-history tracking, recommendations, and revenue reporting.

The project illustrates how inheritance, method overloading, method overriding, polymorphism, and encapsulation can be used to develop a modular and maintainable software application.

