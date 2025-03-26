package exception;

public class GoogleApiException extends RuntimeException {

	private static final long serialVersionUID = 6938531121443898939L;

	public GoogleApiException(String message) {
        super(message);
    }

    public GoogleApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
