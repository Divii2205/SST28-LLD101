public class SendResult {
    public final boolean success;
    public final String message;

    private SendResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static SendResult ok(String message) {
        return new SendResult(true, message);
    }

    public static SendResult fail(String message) {
        return new SendResult(false, message);
    }
}