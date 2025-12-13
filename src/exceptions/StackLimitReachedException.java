package exceptions;

public class StackLimitReachedException extends Exception {
    public StackLimitReachedException(String message) {
        super(message);
    }
}
