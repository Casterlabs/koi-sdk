package co.casterlabs.koi.api.types.user;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPlatform {
    TWITCH("Twitch"),
    TROVO("Trovo"),
    YOUTUBE("YouTube"),
    DLIVE("DLive"),
    KICK("Kick"),
    TIKTOK("TikTok"),

    // Coming up.
    RUMBLE("Rumble"),
    X("𝕏"),

    // Graveyard. R.I.P.
    CAFFEINE("Caffeine"),
    BRIME("Brime"),
    GLIMESH("Glimesh"),
    THETA("Theta"),
    LIVESPACE("LiveSpace"),

    // Other.
    CASTERLABS_SYSTEM("System"),
    CUSTOM_INTEGRATION("Custom Integration 🔧"), // For custom events created by you.
    ;

    public static final Map<String, String> NAMES = Collections.unmodifiableMap(
        Arrays.asList(values())
            .stream()
            .collect(Collectors.toMap((p) -> p.name(), (v) -> v.str))
    );

    private String str;

    @Override
    public String toString() {
        return this.str;
    }

}
