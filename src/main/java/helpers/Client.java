package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client implements Runnable {

    final Socket clientSocket;

    public Client(final Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            while (true) {
                String command = reader.readLine();
                System.out.println("Received command: " + command);
                if (command.toLowerCase().startsWith("ping")) {
                    writer.append("+PONG\r\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
