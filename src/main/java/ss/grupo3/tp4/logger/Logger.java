package ss.grupo3.tp4.logger;

public class Logger {

    private static final Logger INSTANCE = new Logger();
    private Boolean debug = Boolean.FALSE;

    private Logger() {}

    public static void log(String message, LogType type) {
        if(!type.equals(LogType.DEBUG) || INSTANCE.debug) {
            System.out.print(type.getDescription() + " ");
            System.out.println(message);
        }
    }

    public static void setDebug(Boolean debug) {
        INSTANCE.debug = debug;
    }
}