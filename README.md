twitterlite
===========

Task
----

Implement a simplified, console-based version of a Twitter-like social networking application satisfying the scenarios below:
- Posting: Alice can publish messages to a personal timeline
- Reading: Bob can view Alice’s timeline
- Following: Charlie can subscribe to Alice’s and Bob’s timelines, and view them on his wall, an aggregated list of all subscriptions

General requirements
--------------------

- Application must use the console for input and output;
- User submits commands to the application:
-- posting: <user name> -> <message>
-- reading: <user name>
-- following: <user name> follows <another user>
-- wall: <user name> wall
- Don't worry about handling any exceptions or invalid commands. Assume that the user will always type the correct commands. Just focus on the sunny day scenarios.
- Use whatever language and frameworks you want. (provide instructions how to run the application)
- The program should prompt “>”, signifying that the user can enter the commands through the standard input. The program will either reply (e.g. for the view timeline command) or not (posting command), depending on the current state. 
- The state should not be persisted, keeping data in memory is sufficient.
