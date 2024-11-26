package commands;

import java.io.IOException;
import java.util.List;
import helpers.ReadWriteHelper;

public abstract class Command {
    protected ReadWriteHelper readWriteHelper;

    public Command(final ReadWriteHelper readWriteHelper) {
        this.readWriteHelper = readWriteHelper;
    }

    public abstract void handle(final List<String> args) throws IOException;
}
