package com.egeuniversity.onlineeducationsystem.utility;

import com.egeuniversity.onlineeducationsystem.security.services.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.time.LocalDateTime;

public abstract class Utility {

    public void validatePageAndSize(int page, int size) throws Exception {
        if (page <= 0) {
            throw new RuntimeException("Error: Invalid page number. Page must be greater than 0.");
        } else if (size <= 0) {
            throw new RuntimeException("Error: Invalid size number. Size must be greater than 0.");
        }
    }

    public static LocalDateTime getNow() {
        return LocalDateTime.from(Instant.now());
    }

    public static Long getUserIdFromToken() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

   public static String getRoleFromToken() {
       return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
               .get(0);
   }

    public static String getUserNameFromToken() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public static String getUserEmailFromToken() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
    }

}
