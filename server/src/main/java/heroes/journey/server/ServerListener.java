package heroes.journey.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import heroes.journey.Message;

public class ServerListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Message) {
            Message message = (Message)object;
            System.out.println("Received: " + message.text);

            // Example echo back
            connection.sendTCP(new Message("Received: " + message.text));
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Client disconnected: " + connection.getID());
    }
}
