// Configure and rename to "config.js"
// Create an empty file called "sentry" in same directory
// Have steamguard code = "" to start
// Fill in the steamguard code after receiving it
// After successfully logging in, return steamguard code to ""
var config = {};

config.steam_name = "ENTER_HERE";
config.steam_user = "ENTER_HERE";
config.steam_pass = "ENTER_HERE";
config.steam_guard_code = "steamGuardCodeIfApplicable";

module.exports = config;