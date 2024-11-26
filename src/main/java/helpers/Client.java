package helpers;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import commands.Command;
import commands.CommandRegistry;
import static models.Datatype.ARRAYS;

public class Client implements Runnable {

    final CommandRegistry commandRegistry;
    final ReadWriteHelper readWriteHelper;

    public Client(final Socket clientSocket) throws IOException {
        this.readWriteHelper =
                new ReadWriteHelper(clientSocket.getInputStream(), clientSocket.getOutputStream());
        this.commandRegistry = new CommandRegistry(this.readWriteHelper);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String datatype = this.readWriteHelper.readLine();
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
        List<String> arguments = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            this.readWriteHelper.readLine();
            String arg = this.readWriteHelper.readLine();
            arguments.add(arg);
        }

        String operation = arguments.get(0).toLowerCase();
        Command commandHandler = commandRegistry.get(operation);
        if (Objects.isNull(commandHandler)) {
            throw new IOException("Invalid Operation");
        }
        commandHandler.handle(arguments);
    }
}
