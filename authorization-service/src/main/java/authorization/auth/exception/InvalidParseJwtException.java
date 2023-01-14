package authorization.auth.exception;

public class InvalidParseJwtException extends RuntimeException {
    public InvalidParseJwtException() {
        super("Invalid parse jwt. NullPointer.");
    }
}
