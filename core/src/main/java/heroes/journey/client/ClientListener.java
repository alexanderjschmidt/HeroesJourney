package heroes.journey.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import heroes.journey.Message;

public class ClientListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        switch (object) {
            case Message msg -> {
                System.out.println("Received from server: " + msg.text);
            }
            default -> {
                System.out.println("Unknown object: " + object);
            }
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Disconnected from server.");
    }
}
