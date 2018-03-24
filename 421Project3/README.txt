COMP421 - Databases
Project 3, Question 2
Group 31
Done by Anthony Courchesne - 260688650

Project Description:
Our project (Done in previous project submission) is a ESport system where player can be in teams that take part in matches of tournaments. We also keep track of spectators, sponsors, games and others.

Description:
Java GUI application to execute 5 of the main functions of our project using JDBC and the postgreSQL driver to connect to our database on McGill SOCS servers.

Classes:
Application.java has the main class
DatabaseConnection loads the driver and handles all the database calls
All other classes are UI only, MainFrame being the main window. 

Queries:
Q1: Register a player into the database
Q2: Announce the winner of a match and update the score of the winning team
Q3: Get a list of the players of teams that takes part in the given tournament
Q4: Update the date and location of a match
Q5: Disqualify a team: Try to find a random team that plays the same game to replace
the disqualified team in all of its matches, otherwise just remove the team's participation in matches