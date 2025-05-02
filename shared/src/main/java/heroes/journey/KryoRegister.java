package heroes.journey;

import com.esotericsoftware.kryo.Kryo;

import heroes.journey.messages.requests.CreateLobbyRequest;
import heroes.journey.messages.requests.JoinLobbyRequest;
import heroes.journey.messages.responses.LobbyCreatedResponse;
import heroes.journey.messages.responses.LobbyJoinedResponse;
import heroes.journey.messages.responses.UpdateLobbyPlayers;
import heroes.journey.models.Lobby;
import heroes.journey.models.LobbyInfo;
import heroes.journey.models.MapData;

public class KryoRegister {

    public static void registerClasses(Kryo kryo) {
        // Requests
        kryo.register(CreateLobbyRequest.class);
        kryo.register(JoinLobbyRequest.class);
        // Responses
        kryo.register(LobbyCreatedResponse.class);
        kryo.register(LobbyJoinedResponse.class);
        kryo.register(UpdateLobbyPlayers.class);
        // Objects
        kryo.register(Lobby.class);
        kryo.register(LobbyInfo.class);
        kryo.register(MapData.class);
        // Java Objects
        kryo.register(java.util.ArrayList.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(java.util.HashSet.class);
    }

}
