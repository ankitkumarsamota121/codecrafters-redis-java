package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import static models.Datatype.ARRAYS;

public class Client implements Runnable {

    final Socket clientSocket;
    final BufferedReader reader;
    final BufferedWriter writer;
    final Map<String, String> datastore;
    final Map<String, Long> expiryMap;

    public Client(final Socket clientSocket, final Map<String, String> datastore,
            final Map<String, Long> expiryMap) throws IOException {
        this.clientSocket = clientSocket;
        this.datastore = datastore;
        this.expiryMap = expiryMap;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String datatype = reader.readLine();
                if (ARRAYS.getIndicator() == datatype.charAt(0)) {
                    handleArrays(datatype);
                }
            } catch (final IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }

    private void handleArrays(final String datatype) throws IOException {
        int length = Integer.valueOf(datatype.substring(1));
        String[] arguments = new String[length];
        for (int i = 0; i < length; i++) {
            reader.readLine();
            String arg = reader.readLine();
            arguments[i] = arg;
        }
        if (arguments[0].equalsIgnoreCase("echo")) {
            writer.append("+" + arguments[1] + "\r\n");
            writer.flush();
        }
        if (arguments[0].equalsIgnoreCase("ping")) {
            writer.append("+PONG\r\n");
            writer.flush();
        }
        if (arguments[0].equalsIgnoreCase("set")) {
            datastore.put(arguments[1], arguments[2]);
            if (arguments.length > 3 && arguments[3].equalsIgnoreCase("px")) {
                Long timeout = System.currentTimeMillis() + Long.valueOf(arguments[4]);
                expiryMap.put(arguments[1], timeout);
            }
            writer.append("+OK\r\n");
            writer.flush();
        }
        if (arguments[0].equalsIgnoreCase("get")) {
            Long currentTime = System.currentTimeMillis();
            if (currentTime <= expiryMap.getOrDefault(arguments[1], currentTime + 1L)) {
                writer.append("+" + datastore.getOrDefault(arguments[1], "") + "\r\n");
            } else {
                writer.append("$-1\r\n");
            }
            writer.flush();
        }
    }
}
