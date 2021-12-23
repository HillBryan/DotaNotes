package Callbacks;

import com.codedisaster.steamworks.SteamAuth;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamResult;
import com.codedisaster.steamworks.SteamUserCallback;

/**
 * @Author Bryan Hill
 */

public class SteamUserImpl implements SteamUserCallback {
    @Override
    public void onValidateAuthTicket(SteamID steamID, SteamAuth.AuthSessionResponse authSessionResponse, SteamID steamID1) {}

    @Override
    public void onMicroTxnAuthorization(int i, long l, boolean b) {}

    @Override
    public void onEncryptedAppTicket(SteamResult steamResult) {}
}
