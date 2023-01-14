package hastranslator.exception;

public class FailedDecodeRequestException extends RuntimeException {

    public FailedDecodeRequestException() {
        super("Failed decode request. Invalid hash data.");
    }
}
