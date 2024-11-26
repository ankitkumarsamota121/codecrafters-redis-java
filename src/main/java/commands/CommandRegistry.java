package commands;

import java.util.HashMap;
import java.util.Map;
import helpers.ReadWriteHelper;
import models.Constants.Operations;

public class CommandRegistry {
    Map<String, Command> registry;

    public CommandRegistry(final ReadWriteHelper readWriteHelper) {
        this.registry = new HashMap<>();
        this.registry.put(Operations.ECHO, new EchoCommand(readWriteHelper));
        this.registry.put(Operations.PING, new PingCommand(readWriteHelper));
        this.registry.put(Operations.SET, new SetCommand(readWriteHelper));
        this.registry.put(Operations.GET, new GetCommand(readWriteHelper));
        this.registry.put(Operations.CONFIG, new ConfigCommand(readWriteHelper));
    }

    public Command get(final String operation) {
        return registry.get(operation);
    }
}
