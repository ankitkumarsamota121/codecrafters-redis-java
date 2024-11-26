package models;

public final class Constants {
    public static final String NEW_LINE = "\r\n";
    public static final String NIL_BULK_STRING = "$-1\r\n";

    public static final class Operations {
        public static final String PING = "ping";
        public static final String ECHO = "echo";
        public static final String GET = "get";
        public static final String SET = "set";
        public static final String CONFIG = "config";
    }

    private Constants() {}
}
