import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import helpers.Client;
import helpers.ConfigHelper;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible
        // when running tests.
        System.out.println("Logs from your program will appear here!");

        for (int i = 0; i < args.length; i += 2) {
            String key = args[i];
            String value = args[i + 1];
            ConfigHelper.getInstance().put(key, value);
        }

        int port = 6379;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            ExecutorService executor = Executors.newFixedThreadPool(5);

            while (true) {
                // Wait for a new client connection.
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket);
                executor.submit(client);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
