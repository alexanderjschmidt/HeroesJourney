package heroes.journey.server;

import static heroes.journey.ServerDefaults.TCP_PORT;
import static heroes.journey.ServerDefaults.UDP_PORT;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;

import heroes.journey.KryoRegister;

/**
 * Launches the server application.
 */
public class ServerLauncher {

    public void start() throws IOException {
        Server server = new Server();
        KryoRegister.registerClasses(server.getKryo());

        server.start();
        server.bind(TCP_PORT, UDP_PORT);

        server.addListener(new LobbyListener()); // Your listener class for handling messages

        System.out.println("Server started on TCP " + TCP_PORT + " / UDP " + UDP_PORT);
    }

    public static void main(String[] args) {
        try {
            new ServerLauncher().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
