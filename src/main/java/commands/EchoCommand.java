package commands;

import static helpers.RESPEncoder.encodeSimpleString;
import java.io.IOException;
import java.util.List;
import helpers.ReadWriteHelper;

public class EchoCommand extends Command {
    public EchoCommand(final ReadWriteHelper readWriteHelper) {
        super(readWriteHelper);
    }

    @Override
    public void handle(final List<String> args) throws IOException {
        super.readWriteHelper.write(encodeSimpleString(args.get(1)));
    }
}
