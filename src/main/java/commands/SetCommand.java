package commands;

import static helpers.RESPEncoder.encodeSimpleString;
import java.io.IOException;
import java.util.List;
import helpers.DatastoreHelper;
import helpers.ReadWriteHelper;
import models.ValueAndExpiry;

public class SetCommand extends Command {
    public SetCommand(final ReadWriteHelper readWriteHelper) {
        super(readWriteHelper);
    }

    @Override
    public void handle(final List<String> args) throws IOException {
        final ValueAndExpiry toStore = new ValueAndExpiry(args.get(2));
        if (args.size() > 3 && args.get(3).equalsIgnoreCase("px")) {
            Long ttl = System.currentTimeMillis() + Long.valueOf(args.get(4));
            toStore.setTtl(ttl);
        }
        DatastoreHelper.getInstance().put(args.get(1), toStore);
        super.readWriteHelper.write(encodeSimpleString("OK"));
    }

}
