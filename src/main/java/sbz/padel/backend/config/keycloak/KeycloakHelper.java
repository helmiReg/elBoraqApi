package sbz.padel.backend.config.keycloak;
//import org.keycloak.representations.AccessToken;

import java.util.Set;

public interface KeycloakHelper {
    // public AccessToken getAccessToken();

    public String getCurrentUserName();

    public Set<String> getRoles();

    public String getUserId();

    public String getUserGroup();

    public String getUserGroupFullPath();

    public String getTokenString();
}
