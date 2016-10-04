package ss.grupo3.tp4.logger;

public enum LogType {
    SUCCESS("[DONE]"), ERROR("[ERROR]"), INFO("[INFO]"), WARNING("[WARNING]"), DEBUG("[DEBUG]");

    private String description;

    /*package*/ LogType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}