package hastranslator.exception;

public class DecodeRequestNotFoundException extends RuntimeException {
    public DecodeRequestNotFoundException(String id) {
        super("Decode Request not found. RequestId: " + id);
    }
}
