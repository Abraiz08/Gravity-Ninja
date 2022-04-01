# Gravity Ninja

## A hyper casual gravity based game

**What will the application do?**

The application is a java based game that puts the player in an enclosed environment. The main goal is for the player 
is to collect "points" that appear around the screen. During this, obstacles appear on the screen from any of the four 
edges of the arena. These obstacles move from one side of the arena across the screen until they reach the 
opposite edge. If the player makes contact with these obstacles, the game ends. In order to dodge these the player 
can jump, double jump, and flip the gravity of the entire arena.

**Who will use it?**

The game is focused towards gamers who want a casual, fast paced experience to kill boredom.

**Why is this project of interest to you?**

This project is of interest to me as my ultimate goal as a computer science student is to be a game designer. Designing
a casual game such as this will allow me to understand what makes game enjoyable and replayable.


## User Story:
- As a user, I want to avoid obstacles that appear in the arena, these obstacles added to a list of obstacles moving 
around the arena.
- As a user, I want to be able to jump and double jump using the space bar.
- As a user, I want to be able to flip the gravity of the arena using the 'x' key.
- As a user, I want to be able to move left and right across the arena floor, or the ceiling depending on the direction 
of gravity, using the arrow keys.
- As a user, I want to be able to collect points that appear in the arena and increase my score.
- As a user, I want to be able to save the state of my game, including the positions of all objects, the player 
character, and the score to file using the 's' key, or through the menu bar.
- As a user, I want to be given the option to load my game state from file using the 'l' key, or through the menu bar.
- As a user, I want to be able to exit the game through then menu bar.

## Phase 4: task 2:

Tue Mar 29 19:12:08 PDT 2022
Spawned obstacle model.Obstacle@5d2bb922 at 775, 596.


Tue Mar 29 19:12:08 PDT 2022
Spawned obstacle model.Obstacle@32f60657 at 1008, 4.


Tue Mar 29 19:12:08 PDT 2022
Spawned obstacle model.Obstacle@32144b82 at 2, 159.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:08 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.


Tue Mar 29 19:12:09 PDT 2022
Moved obstacle model.Obstacle@5d2bb922 upwards.


Tue Mar 29 19:12:09 PDT 2022
Moved obstacle model.Obstacle@32f60657 downwards.


Tue Mar 29 19:12:09 PDT 2022
Moved obstacle model.Obstacle@32144b82 to the right.

## Phase 4: task 3:

GravityNinja contains a number of individual components such as PlayerCharacter and Obstacle. The Game class contains 
each of these classes as fields, and they are initialized as objects. All the behaviour is contained within these 
classes, and they are all implemented to work with one another in the Game class. The ui uses Game as a field in order 
to display everything together.

If I had more time to develop this project, I would perform the following refactorings:

-At the moment, each class in the ui package (except SplashScreen) contains a field of the Game class, which is then 
assigned to be the same game as GNGame in the constructor. I would change it so that there is only one Game in GNGame,
and the other classes have a field of GNGame, through which Game is manipulated.

-Obstacle and PlayerCharacter both have fields of the class Position, and a point on the map is represented by a 
Position object. This setup can be refactored into a hierarchy, in which I will create a new class 'Point' and make 
Position a superclass of PlayerCharacter, Obstacle, and Point.
