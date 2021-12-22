# DotaNotes
  - Service to save notes about dota 2 players. The user can then be notified of these notes when they queue against any of these tagged players. After the game ends, the user is given the option to write notes on the players in their game.

# How to use
  - Any user who wishes to use this service will add the Steam Bot (dotarevenge004)
  - When one enters a dota 2 game, this bot will message them with any saved notes on players in the game and a link to view these notes.
  - After the game, the bot will message the user and give a link that can be used to take notes on the players in the match.
  
# How it works
  - ## The bot
    - SteamWorks is used for rich presence callbacks.
    - SteamKit2 is used to send steam messages from server side.
    - When a rich presence change is detected to show a friend has joined a dota 2 match, the server_steam_id is gathered and the Steam Dota 2 /getLiveStats endpoint is called to get accountIDs of players in the game. The DB is then polled via REST API to look for saved notes on these accountIDs. If any notes are found, a front-end link is generated containing these notes and is sent to the friend in game.
    - When a rich presence change is detected to show a friend left a dota 2 match, a front-end link is generated where comments can be saved for players in the match.
  - ## Getting server_steam_id
    - High Level: A bot is used to spectate the game of a given 64 bit steamID. Once the bot is spectating, the server_steam_id can be taken from the Game Coordinator response. 
    - **This technique works on anyone, not only players on the bots friends list.**

# Frameworks / Packages used
  - [SteamWorks](https://partner.steamgames.com/)
  - [SteamKit2](https://github.com/Longi94/JavaSteam)
  - [npm dota2](https://www.npmjs.com/package/dota2)
  - Vue
  - Java RestEasy
  - Express
  - NodeJS
  
 # APIs used
  - [OpenDota API](https://docs.opendota.com/)
  - [Valve Dota API](https://wiki.teamfortress.com/wiki/WebAPI#Dota_2)

