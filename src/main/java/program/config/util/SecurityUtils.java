package program.config.util;

import org.springframework.security.core.context.SecurityContextHolder;
import program.model.UserDetails;

public final class SecurityUtils {
    public static UserDetails getCurrentAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        return null;
    }
}
