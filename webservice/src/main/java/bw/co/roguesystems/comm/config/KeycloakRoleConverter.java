package bw.co.roguesystems.comm.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // --- Realm roles ---
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            ((Collection<String>) realmAccess.get("roles"))
                .forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(
                        "ROLE_" + role.toUpperCase()
                    ))
                );
        }

        // --- Client roles (flattened) ---
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess != null) {
            resourceAccess.forEach((clientId, clientData) -> {
                Map<String, Object> clientRoles = (Map<String, Object>) clientData;
                if (clientRoles.containsKey("roles")) {
                    ((Collection<String>) clientRoles.get("roles"))
                        .forEach(role ->
                            authorities.add(new SimpleGrantedAuthority(
                                "ROLE_" + clientId.toUpperCase() + "_" + role.toUpperCase()
                            ))
                        );
                }
            });
        }

        // --- Optional: scopes ---
        String scope = jwt.getClaimAsString("scope");
        if (scope != null && !scope.isBlank()) {
            Arrays.stream(scope.split(" "))
                .forEach(s ->
                    authorities.add(new SimpleGrantedAuthority(s.toUpperCase()))
                );
        }

        return authorities;
    }
}
