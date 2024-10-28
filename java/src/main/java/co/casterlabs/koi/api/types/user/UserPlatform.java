package co.casterlabs.koi.api.types.user;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum UserPlatform {
    TWITCH("Twitch", "https://twitch.tv"),
    TROVO("Trovo", "https://trovo.live"),
    YOUTUBE("YouTube", "https://youtube.com"),
    DLIVE("DLive", "https://dlive.tv"),
    KICK("Kick", "https://kick.com"),
    TIKTOK("TikTok", "https://tiktok.com"),

    // Coming up.
    NOICE("Noice", "https://noice.com"),
    RUMBLE("Rumble", "https://rumble.com"),
    X("𝕏", "https://x.com"),

    // Graveyard. R.I.P.
    CAFFEINE("Caffeine", "https://caffeine.tv"),
    BRIME("Brime", "https://brime.tv"),
    GLIMESH("Glimesh", "https://glimesh.tv"),
    THETA("Theta", "https://theta.tv"),
    LIVESPACE("LiveSpace", "https://live.space"),

    // Other.
    CASTERLABS_SYSTEM("System", ""),
    CUSTOM_INTEGRATION("Custom Integration 🔧", ""), // For custom events created by you.
    ;

    public static final Map<String, String> NAMES = Collections.unmodifiableMap(
        Arrays.asList(values())
            .stream()
            .collect(Collectors.toMap((p) -> p.name(), (v) -> v.friendlyName))
    );

    public final SimpleProfile systemProfile;
    public final User systemUser;
    public final String friendlyName;

    private UserPlatform(String friendlyName, String link) {
        this.friendlyName = friendlyName;

        this.systemProfile = SimpleProfile.builder()
            .bothIds(this.name())
            .platform(this)
            .build();

        this.systemUser = User.builder(this.systemProfile)
            .username(this.name())
            .displayname(this.friendlyName)
            .link(link)
            .build();
    }

    @Override
    public String toString() {
        return this.friendlyName;
    }

}
