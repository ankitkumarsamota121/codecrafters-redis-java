package helpers;

import static models.Constants.NEW_LINE;

public final class RESPEncoder {
    public static String encodeSimpleString(final String data) {
        return "+" + data + NEW_LINE;
    }

    public static String encodeBulkString(final String data) {
        return "$" + data.length() + NEW_LINE + data + NEW_LINE;
    }

    public static String encodeArrays(final String[] dataArr) {
        String res = "*" + dataArr.length + NEW_LINE;
        for (String data : dataArr) {
            res += encodeBulkString(data);
        }
        return res;
    }
}
