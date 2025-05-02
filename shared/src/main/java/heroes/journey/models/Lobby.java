package heroes.journey.models;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;

public class Lobby {
    public String id;
    public MapData mapData;
    public Connection hostConnection;
    public String hostName;
    public ArrayList<Connection> players = new ArrayList<>();
    public ArrayList<String> playerNames = new ArrayList<>();

    public Lobby(String id, Connection host, String hostName) {
        this.id = id;
        this.hostConnection = host;
        players.add(host);
        playerNames.add(hostName);
    }

    public boolean isFull() {
        return players.size() >= 6;
    }
}
