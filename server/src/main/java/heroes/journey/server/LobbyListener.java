package heroes.journey.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import heroes.journey.messages.requests.CreateLobbyRequest;
import heroes.journey.messages.requests.JoinLobbyRequest;
import heroes.journey.messages.responses.LobbyCreatedResponse;
import heroes.journey.messages.responses.LobbyJoinedResponse;
import heroes.journey.messages.responses.UpdateLobbyPlayers;
import heroes.journey.models.Lobby;
import heroes.journey.models.LobbyInfo;

public class LobbyListener extends Listener {

    HashMap<String,Lobby> lobbies = new HashMap<>();

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof CreateLobbyRequest req) {
            String lobbyId = UUID.randomUUID().toString().substring(0, 6);
            Lobby lobby = new Lobby(lobbyId, connection, req.playerName);
            lobbies.put(lobbyId, lobby);

            LobbyInfo info = new LobbyInfo();
            info.lobbyId = lobbyId;
            info.hostName = req.playerName;
            info.playerNames = new ArrayList<>(lobby.playerNames);
            info.isFull = false;

            connection.sendTCP(new LobbyCreatedResponse() {{lobby = info;}});
        }

        if (object instanceof JoinLobbyRequest req) {
            Lobby lobby = lobbies.get(req.lobbyId);
            if (lobby != null && !lobby.isFull()) {
                lobby.players.add(connection);
                lobby.playerNames.add(req.playerName);

                LobbyInfo info = new LobbyInfo();
                info.lobbyId = lobby.id;
                info.hostName = lobby.hostName;
                info.playerNames = new ArrayList<>(lobby.playerNames);
                info.isFull = lobby.isFull();

                // Send joined response to the joining player
                connection.sendTCP(new LobbyJoinedResponse() {{lobby = info;}});

                // Notify all lobby players of updated list
                UpdateLobbyPlayers update = new UpdateLobbyPlayers();
                update.lobbyId = lobby.id;
                update.playerNames = new ArrayList<>(lobby.playerNames);
                for (Connection c : lobby.players) {
                    c.sendTCP(update);
                }
            }
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Client disconnected: " + connection.getID());
    }
}
