package com.github.sbadolankaur.rpg.identity.application;

import com.github.sbadolankaur.rpg.identity.domain.AppUser;
import com.github.sbadolankaur.rpg.identity.domain.InstanceState;
import com.github.sbadolankaur.rpg.identity.persistence.AppUserRepository;
import com.github.sbadolankaur.rpg.identity.persistence.InstanceStateRepository;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private static final Short INSTANCE_STATE_ID = (short) 1;
    private static final int BCRYPT_MAX_BYTES = 72;

    private final AppUserRepository appUserRepository;
    private final InstanceStateRepository instanceStateRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            AppUserRepository appUserRepository,
            InstanceStateRepository instanceStateRepository,
            PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.instanceStateRepository = instanceStateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(String suppliedUsername, String suppliedDisplayName, String password) {
        InstanceState state = instanceStateRepository
                .lockById(INSTANCE_STATE_ID)
                .orElseThrow(() -> new IllegalStateException("Instance state row is missing."));

        if (state.isInitialized()) {
            throw new RegistrationClosedException();
        }

        String username = suppliedUsername.strip().toLowerCase(Locale.ROOT);
        if (appUserRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }
        if (password.getBytes(StandardCharsets.UTF_8).length > BCRYPT_MAX_BYTES) {
            throw new PasswordTooLongException();
        }

        appUserRepository.save(
                new AppUser(
                        username,
                        passwordEncoder.encode(password),
                        suppliedDisplayName.strip(),
                        true,
                        Instant.now()));
        state.initialize();
    }

    @Transactional(readOnly = true)
    public boolean isRegistrationOpen() {
        return instanceStateRepository
                .findById(INSTANCE_STATE_ID)
                .map(state -> !state.isInitialized())
                .orElse(false);
    }
}
