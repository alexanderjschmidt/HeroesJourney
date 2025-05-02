package heroes.journey.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import heroes.journey.messages.responses.LobbyCreatedResponse;
import heroes.journey.messages.responses.LobbyJoinedResponse;
import heroes.journey.messages.responses.UpdateLobbyPlayers;

public class LobbyListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof LobbyCreatedResponse res) {
            System.out.println("Created lobby: " + res.lobby.lobbyId);
            // Show lobby UI
        }

        if (object instanceof LobbyJoinedResponse res) {
            System.out.println("Joined lobby: " + res.lobby.lobbyId);
            // Show lobby UI
        }

        if (object instanceof UpdateLobbyPlayers update) {
            System.out.println("Updated players: " + update.playerNames);
            // Update lobby UI
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Disconnected from server.");
    }
}
