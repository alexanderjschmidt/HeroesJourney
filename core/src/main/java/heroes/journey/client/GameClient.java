package heroes.journey.client;

import static heroes.journey.ServerDefaults.TCP_PORT;
import static heroes.journey.ServerDefaults.UDP_PORT;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

import heroes.journey.KryoRegister;
import heroes.journey.Message;

public class GameClient {
    
    private static final String SERVER_IP = "localhost"; // Change to your server IP

    private Client client;

    public void start() throws IOException {
        client = new Client();
        KryoRegister.registerClasses(client.getKryo());

        client.start();
        client.connect(5000, SERVER_IP, TCP_PORT, UDP_PORT);

        client.addListener(new ClientListener());

        // Example of sending a message once connected
        Message message = new Message("Hello from client!");
        client.sendTCP(message);

        System.out.println("Client started and connected to server.");
    }
}
