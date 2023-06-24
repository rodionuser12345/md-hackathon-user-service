package md.hackathon.springboot.user_service_app.exception;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String token, String message) {
        super(token + ", " + message);
    }
}
