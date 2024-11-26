package commands;

import static helpers.RESPEncoder.encodeSimpleString;
import static models.Constants.NIL_BULK_STRING;
import java.io.IOException;
import java.util.List;
import helpers.DatastoreHelper;
import helpers.ReadWriteHelper;
import models.ValueAndExpiry;

public class GetCommand extends Command {
    public GetCommand(final ReadWriteHelper readWriteHelper) {
        super(readWriteHelper);
    }


    @Override
    public void handle(final List<String> args) throws IOException {
        Long currentTime = System.currentTimeMillis();
        ValueAndExpiry cached = DatastoreHelper.getInstance().get(args.get(1));
        if (cached != null && currentTime <= cached.getTtl()) {
            super.readWriteHelper.write(encodeSimpleString(cached.getValue()));
        } else {
            super.readWriteHelper.write(NIL_BULK_STRING);
        }
    }
}
