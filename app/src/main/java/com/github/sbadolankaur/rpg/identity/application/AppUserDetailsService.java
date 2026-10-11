package com.github.sbadolankaur.rpg.identity.application;

import com.github.sbadolankaur.rpg.identity.domain.AppUser;
import com.github.sbadolankaur.rpg.identity.persistence.AppUserRepository;
import com.github.sbadolankaur.rpg.platform.security.AppUserPrincipal;
import java.util.Locale;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        AppUser user = appUserRepository
                .findByUsername(username.strip().toLowerCase(Locale.ROOT))
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new AppUserPrincipal(user);
    }
}
