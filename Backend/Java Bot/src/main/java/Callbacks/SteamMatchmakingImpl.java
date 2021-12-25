package Callbacks;

import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamMatchmaking;
import com.codedisaster.steamworks.SteamMatchmakingCallback;
import com.codedisaster.steamworks.SteamResult;

public class SteamMatchmakingImpl implements SteamMatchmakingCallback {
    @Override
    public void onFavoritesListChanged(int i, int i1, int i2, int i3, int i4, boolean b, int i5) {

    }

    @Override
    public void onLobbyInvite(SteamID steamID, SteamID steamID1, long l) {

    }

    @Override
    public void onLobbyEnter(SteamID steamID, int i, boolean b, SteamMatchmaking.ChatRoomEnterResponse chatRoomEnterResponse) {

    }

    @Override
    public void onLobbyDataUpdate(SteamID steamID, SteamID steamID1, boolean b) {

    }

    @Override
    public void onLobbyChatUpdate(SteamID steamID, SteamID steamID1, SteamID steamID2, SteamMatchmaking.ChatMemberStateChange chatMemberStateChange) {

    }

    @Override
    public void onLobbyChatMessage(SteamID steamID, SteamID steamID1, SteamMatchmaking.ChatEntryType chatEntryType, int i) {

    }

    @Override
    public void onLobbyGameCreated(SteamID steamID, SteamID steamID1, int i, short i1) {

    }

    @Override
    public void onLobbyMatchList(int i) {

    }

    @Override
    public void onLobbyKicked(SteamID steamID, SteamID steamID1, boolean b) {

    }

    @Override
    public void onLobbyCreated(SteamResult steamResult, SteamID steamID) {

    }

    @Override
    public void onFavoritesListAccountsUpdated(SteamResult steamResult) {

    }
}
