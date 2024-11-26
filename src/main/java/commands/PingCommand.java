package commands;

import java.io.IOException;
import java.util.List;
import helpers.ReadWriteHelper;
import static helpers.RESPEncoder.encodeSimpleString;

public class PingCommand extends Command {
    public PingCommand(final ReadWriteHelper readWriteHelper) {
        super(readWriteHelper);
    }

    @Override
    public void handle(final List<String> args) throws IOException {
        super.readWriteHelper.write(encodeSimpleString("PONG"));
    }

}
