/**
 * Used to gather live game data (work-in-progress)
 */

// Require statements for fetch API
require('es6-promise').polyfill();
require('isomorphic-fetch');

bot = null;

var util = require('util'),
    bigNumber = require('big-number')
    emitter = require('../app').emitter;

/**
 * Gets the live match data for someone
 */
var CreateLiveGameDataRequest = function (bot, steamId, res)
{
  // Check if the bot is connected to Steam Client.
  // and logged into Steam, and connected to Dota 2 GC
  if (!bot.steamClientConnected && !bot.steamUserConnected && !bot.dotaConnected)
    return;

  // Create live game data request
  request = { "SteamId": steamId, "Response": res };

  this.bot = bot;
  bot.addLiveGameDataRequest(request);

  // The request handler should be built into app.js on an interval.
};

/**
 * API returned 400. Clear the request for now.
 */
emitter.on('NotPlayingRequest', function (bot) {
  bot.currentRequest.Response.send('SteamID not currently in game.');
  bot.currentRequest = null;
  util.log('GatherLiveDataBadRequest successfully handled.');
});

// Live Game Data handlers
emitter.on('ReadyToGatherLiveGameData', function (bot) {
  util.log('System gathering live game data for lobby: ' + bot.currentLobby)
  emitter.emit('LobbyIDResponse', bot);
});

/**
 * API returned 400. Clear the request for now.
 */
emitter.on('LobbyIDResponse', function (bot) {
  bot.currentRequest.Response.send(bot.currentLobby.toString());
  bot.currentRequest = null;
  util.log('GatherLiveDataBadRequest successfully handled.');
});

// emitter.on('spectateFriendGameResp', function(server_steam_id) {
//   this.bot.currentRequest.Response.send(server_steam_id);
// });

/**
 * API successfully returned lobby ID info.
 */
emitter.on('CompletedGatheringLiveData', function (bot, lobbyIds) {
  bot.currentRequest.Response.send('The lobby IDs are: ' + lobbyIds.toString());
  bot.currentRequest = null;
  util.log('CompletedGatheringLiveData successfully handled.');
});

function BuildLiveGameDataAPIRequest(currentLobbyId)
{
  var headers = new Headers({
    'Host': 'api.steampowered.com',
    'Accept': 'application/json',
    'Accept-Language': 'en-CA',
    'Accept-Encoding': 'gzip',
    'Connection': 'close'
  });

  var init = {
    method: 'GET',
    headers: headers
  }

  return new Request(BuildLiveGameDataAPIUrl(currentLobbyId), init);
}

/**
 * Build the API url for getting Live Game Data.
 */
function BuildLiveGameDataAPIUrl(currentLobbyId)
{
  return 'https://api.steampowered.com/IDOTA2MatchStats_570/GetRealtimeStats/v1'
         + '?key=' + process.env.API_KEY
         + '&server_steam_id=' + currentLobbyId;
};

/**
 * Dota 2 uses its own account id system, must convert to Steam id.
 */
function ConvertDotaIdToSteamId(id)
{
  // TODO: See whatsmysteamid.azurewebsites.net
  return bigNumber(id).add(76561197960265728).toString();
}

exports.CreateLiveGameDataRequest = CreateLiveGameDataRequest;