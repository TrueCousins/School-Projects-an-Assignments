# Purpose
To implement the different search algorithms we learned that semester and apply it to the project. This project was a competition for 
the best grade in the class. I worked with a partner. We had several search algorithms that were discussed during the semester and 
could use any of them to help our program. 

# My role
I worked mostly on the coding for the defensive player. The player is able to intercept the enemies and take them out. If the enemy has the flag,
my player would switch to a defensive position and retrieve the flag back. In addition, I helped write the searching algorithm for our 
players to use to find the shortest path back to our base to score a point. 
# Planning
Since we had two players to work with, we went with a balance approach. I worked on a defensive player, the player guarding the base,
and my partner worked on the attacker, the player actively searching for the flag
# Code Description

A java program that allows a user to add their player, or Agent, into the code. A user is able to select the type of board the players 
will compete on, which side each team plays on, and select which teams ,out of the team list, to go against each other. 

Once the player has made their selection, the game can start. Each team moves around the board searching for the other teams' flag. 
An Agent should be able to navigate through the board and find the flag. Agents can only see one square forward, backwards, and beside
them. The first team that is able to grab their opponents' flag and successfully bring it back to their base will win. There are several 
different boards that have differnt pattern obstacles on the board. In addition to obstacles, if two playeres hit eachother, they are 
respawned back at their designated base. Agents are able to put 'mines' down that can cause players to respawn back at their
designated space. 
