package commands;

import static helpers.RESPEncoder.encodeArrays;
import java.io.IOException;
import java.util.List;
import helpers.ConfigHelper;
import helpers.ReadWriteHelper;
import models.Constants.Operations;

public class ConfigCommand extends Command {
    public ConfigCommand(final ReadWriteHelper readWriteHelper) {
        super(readWriteHelper);
    }

    @Override
    public void handle(final List<String> args) throws IOException {
        switch (args.get(1).toLowerCase()) {
            case Operations.GET:
                String key = args.get(2);
                String value = ConfigHelper.getInstance().get("--" + key);
                super.readWriteHelper.write(encodeArrays(new String[] {key, value}));
                break;
            default:
                break;
        }
    }
}
