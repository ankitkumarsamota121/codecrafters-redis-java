package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public final class ReadWriteHelper {

    private final BufferedWriter writer;
    private final BufferedReader reader;

    public ReadWriteHelper(final InputStream is, final OutputStream os) {
        this.reader = new BufferedReader(new InputStreamReader(is));
        this.writer = new BufferedWriter(new OutputStreamWriter(os));
    }

    public void write(final String data) throws IOException {
        this.writer.append(data);
        this.writer.flush();
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }
}

