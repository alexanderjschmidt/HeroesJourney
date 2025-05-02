package heroes.journey.models;

import java.util.ArrayList;

public class LobbyInfo {
    public String lobbyId;
    public String hostName;
    public ArrayList<String> playerNames = new ArrayList<>();
    public boolean isFull;
}
