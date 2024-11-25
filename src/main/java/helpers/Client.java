package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import models.ValueAndExpiry;
import static models.Datatype.ARRAYS;

public class Client implements Runnable {

    final Socket clientSocket;
    final BufferedReader reader;
    final BufferedWriter writer;
    final Map<String, ValueAndExpiry> datastore;

    public Client(final Socket clientSocket, final Map<String, ValueAndExpiry> datastore)
            throws IOException {
        this.clientSocket = clientSocket;
        this.datastore = datastore;
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
            final ValueAndExpiry toStore = new ValueAndExpiry(arguments[2]);
            if (arguments.length > 3 && arguments[3].equalsIgnoreCase("px")) {
                Long ttl = System.currentTimeMillis() + Long.valueOf(arguments[4]);
                toStore.setTtl(ttl);
            }
            datastore.put(arguments[1], toStore);
            writer.append("+OK\r\n");
            writer.flush();
        }
        if (arguments[0].equalsIgnoreCase("get")) {
            Long currentTime = System.currentTimeMillis();
            ValueAndExpiry cached = datastore.get(arguments[1]);
            if (cached != null && currentTime <= cached.getTtl()) {
                writer.append("+" + cached.getValue() + "\r\n");
            } else {
                writer.append("$-1\r\n");
            }
            writer.flush();
        }
    }
}
