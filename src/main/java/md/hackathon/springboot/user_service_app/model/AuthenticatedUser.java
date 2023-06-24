package md.hackathon.springboot.user_service_app.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthenticatedUser extends User {
    private final String token;

    public AuthenticatedUser(String username, String token, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

