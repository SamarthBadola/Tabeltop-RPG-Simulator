package com.github.sbadolankaur.rpg.platform.security;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LoginAttemptLimiter {
    private static final long WINDOW_NANOS = Duration.ofMinutes(15).toNanos();
    private static final int MAX_BUCKETS = 4096;
    private static final int MAX_FAILURES_PER_USERNAME_IP = 10;
    private static final int MAX_FAILURES_PER_IP = 60;

    private final Map<String, AttemptWindow> windows =
            new LinkedHashMap<>(256, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, AttemptWindow> eldest) {
                    return size() > MAX_BUCKETS;
                }
            };

    public synchronized boolean isBlocked(String username, String remoteAddress) {
        long now = System.nanoTime();
        return failures(ipKey(remoteAddress), now) >= MAX_FAILURES_PER_IP
                || failures(userIpKey(username, remoteAddress), now)
                        >= MAX_FAILURES_PER_USERNAME_IP;
    }

    public synchronized void recordFailure(String username, String remoteAddress) {
        long now = System.nanoTime();
        increment(ipKey(remoteAddress), now);
        increment(userIpKey(username, remoteAddress), now);
    }

    public synchronized void recordSuccess(String username, String remoteAddress) {
        windows.remove(userIpKey(username, remoteAddress));
    }

    private int failures(String key, long now) {
        AttemptWindow window = windows.get(key);
        if (window == null) {
            return 0;
        }
        if (now - window.startedAtNanos >= WINDOW_NANOS) {
            windows.remove(key);
            return 0;
        }
        return window.failures;
    }

    private void increment(String key, long now) {
        AttemptWindow window = windows.get(key);
        if (window == null || now - window.startedAtNanos >= WINDOW_NANOS) {
            window = new AttemptWindow(now);
        }
        window.failures++;
        windows.put(key, window);
    }

    private String ipKey(String remoteAddress) {
        return "ip:" + remoteAddress;
    }

    private String userIpKey(String username, String remoteAddress) {
        String normalized = username == null ? "" : username.strip().toLowerCase(Locale.ROOT);
        if (normalized.length() > 50) {
            normalized = normalized.substring(0, 50);
        }
        return "user:" + normalized + "|ip:" + remoteAddress;
    }

    private static final class AttemptWindow {
        private final long startedAtNanos;
        private int failures;

        private AttemptWindow(long startedAtNanos) {
            this.startedAtNanos = startedAtNanos;
        }
    }
}
